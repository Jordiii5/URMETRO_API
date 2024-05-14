package com.example.routes.sdsd

import com.example.dao.daoUsuario
import com.example.model.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Defineix les rutes per al registre i login dels usuaris.
 */
fun Routing.loginRegisterRouting() {
    route("user") {
        post("/register") {
            val user = call.receive<Usuari>()
            val esRegistrat = registrarUsuari(user)
            if (esRegistrat) {
                call.respondText("Usuari registrat amb èxit", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Error en el registre de l'usuari", status = HttpStatusCode.InternalServerError)
            }
        }
        post("/login") {
            val user = call.receive<Usuari>()
            val esValid = validarCredencials(user.usuari_dni,user.usuari_contra)

            if (esValid){
                call.respondText("Login correcte", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Login incorrecte", status = HttpStatusCode.Conflict)
            }
        }
    }
}

/**
 * Valida les credencials de l'usuari comprovant el DNI i la contrasenya.
 *
 * @param usuari_dni El DNI de l'usuari.
 * @param usuari_contra La contrasenya de l'usuari.
 * @return `true` si les credencials són vàlides, `false` en cas contrari.
 */
fun validarCredencials(usuari_dni:String, usuari_contra:String):Boolean{
    return transaction {
        val user = Usuaris.select{
            Usuaris.usuari_dni eq usuari_dni
        }.singleOrNull() ?: return@transaction false

        return@transaction user[Usuaris.usuari_contra] == usuari_contra
    }
}

/**
 * Registra un nou usuari a la base de dades.
 *
 * @param user L'objecte [Usuari] que conté les dades de l'usuari a registrar.
 * @return `true` si l'usuari ha estat registrat amb èxit, `false` si l'usuari ja existeix.
 */
suspend fun registrarUsuari(user: Usuari): Boolean {
    // Verificar si l'usuari ja existeix a la base de dades
    val existingUser = daoUsuario.usuarioDni(user.usuari_dni)
    if (existingUser != null) {
        return false // L'usuari ja existeix, no es pot registrar de nou
    }

    // Inserir el nou usuari a la base de dades utilitzant daoUsuario
    daoUsuario.addNewUsuario(
        user.usuari_nom,
        user.usuari_dni,
        user.usuari_telefon,
        user.usuari_contacte_emergencia,
        user.usuari_contra
    )

    return true
}
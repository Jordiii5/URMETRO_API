package com.example.routes.sdsd

import com.example.dao.daoUsuario
import com.example.model.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction


fun Routing.loginRegisterRouting() {
    route("user") {
//        post("/register") {
//            val user = call.receive<Usuari>()
//            val usersFromDb = uploadUser()
//            if (usersFromDb.containsKey(user.usuari_dni)) {
//                call.respondText("Aquest usuari ja existeix", status = HttpStatusCode.Conflict)
//                return@post
//            } else {
//                daoUsuario.addNewUsuario(user.usuari_nom, user.usuari_dni, user.usuari_adreça, user.usuari_telefon, user.usuari_contacte_emergencia, user.usuari_imatge, user.usuari_contra)
//                userTable[user.usuari_dni] = getMd5Digest("${user.usuari_dni}:$myRealm:${user.usuari_contra}")
//                call.respondText("Usuari registrat amb exit", status = HttpStatusCode.Accepted)
//            }
//        }
//        post("/login") {
//            val user = call.receive<Usuari>()
//            userTable = uploadUser()
//            val userHidden = getMd5Digest("${user.usuari_dni}:$myRealm:${user.usuari_contra}")
//            if (userTable.containsKey(user.usuari_dni) && userTable[user.usuari_dni]?.contentEquals(userHidden) == true) {
//                call.respondText("Login correcte", status = HttpStatusCode.Accepted)
//                return@post
//            } else {
//                call.respondText("Login incorrecte", status = HttpStatusCode.Conflict)
//            }
//        }
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

fun validarCredencials(usuari_dni:String, usuari_contra:String):Boolean{
    return transaction {
        val user = Usuaris.select{
            Usuaris.usuari_dni eq usuari_dni
        }.singleOrNull() ?: return@transaction false

        return@transaction user[Usuaris.usuari_contra] == usuari_contra
    }
}
suspend fun registrarUsuari(user: Usuari): Boolean {
    // Verificar si el usuario ya existe en la base de datos
    val existingUser = daoUsuario.usuarioDni(user.usuari_dni)
    if (existingUser != null) {
        return false // Usuario ya existe, no se puede registrar de nuevo
    }

    // Insertar el nuevo usuario en la base de datos utilizando daoUsuario
    daoUsuario.addNewUsuario(
        user.usuari_nom,
        user.usuari_dni,
        user.usuari_adreça,
        user.usuari_telefon,
        user.usuari_contacte_emergencia,
        user.usuari_imatge,
        user.usuari_contra
    )

    return true
}
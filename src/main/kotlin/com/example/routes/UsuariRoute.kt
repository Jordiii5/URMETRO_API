package com.example.routes

import com.example.dao.daoUsuario
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * Defineix les rutes per a les operacions CRUD dels usuaris.
 */
fun Routing.usuariRouting() {
    route("/usuaris") {

        /**
         * Ruta per obtenir la llista de tots els usuaris.
         */
        get {
            val llistaUsuari = daoUsuario.allUsuaris()
            if (llistaUsuari.isNotEmpty()) {
                call.respond(llistaUsuari)
            } else {
                call.respondText("No s'han trobat usuaris")
            }
        }

        /**
         * Ruta per obtenir un usuari per DNI.
         */
        get("/{usuari_dni}") {
            val username = call.parameters["usuari_dni"]?: return@get call.respondText(
                "Dni del usuari incorrecte",
                status = HttpStatusCode.BadRequest
            )
            val usuari = daoUsuario.usuarioDni(username)?: return@get call.respondText(
                "No existeix cap usuari amb aquest dni",
                status = HttpStatusCode.NotFound
            )
            call.respond(usuari)
        }

        /**
         * Ruta per obtenir un usuari per ID.
         */
        get("/{usuari_id}") {
            val username = call.parameters["usuari_id"]?: return@get call.respondText(
                "Id del usuari incorrecte",
                status = HttpStatusCode.BadRequest
            )
            val usuari = daoUsuario.usuario(username.toInt())?: return@get call.respondText(
                "No existeix cap usuari amb aquesta id",
                status = HttpStatusCode.NotFound
            )
            call.respond(usuari)
        }

        /**
         * Ruta per eliminar un usuari amb el seu DNI.
         */
        delete("/{usuari_dni}") {
            val usuari_dni = call.parameters["usuari_dni"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (daoUsuario.deleteUsuario(usuari_dni)) {
                call.respondText("Usuari eliminat correctament", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("No s'ha pogut eliminar l'usuari", status = HttpStatusCode.InternalServerError)
            }
        }

        /**
         * Ruta per actualitzar les dades d'un usuari.
         */
        put("/update/dades/{usuari_dni}/{usuari_nom}/{usuari_telefon}/{usuari_contacte_emergencia}") {
            val usuari_dni: String
            val usuari_nom: String
            val usuari_telefon: Int
            val usuari_contacte_emergencia: Int
            try {
                usuari_dni = call.parameters["usuari_dni"].toString()
                usuari_nom = call.parameters["usuari_nom"].toString()
                usuari_telefon = call.parameters["usuari_telefon"]!!.toInt()
                usuari_contacte_emergencia = call.parameters["usuari_contacte_emergencia"]!!.toInt()

                if (daoUsuario.updateUsuario(usuari_dni, usuari_nom, usuari_telefon, usuari_contacte_emergencia)) {
                    call.respondText("S'ha actualiztat correctmaent", status = HttpStatusCode.OK)
                } else {
                    call.respondText("No s'ha pogut fer l'update", status = HttpStatusCode.InternalServerError)
                }
            } catch (e: NumberFormatException) {
                call.respondText("[ERROR] en el parametre.", status = HttpStatusCode.BadRequest)
            }
        }
    }
}
package com.example.routes

import com.example.dao.daoUsuario
import com.example.model.Usuari
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.usuariRouting() {
    authenticate("myAuth") {
        route("/usuari") {
           get {
               val llistaUsuari = daoUsuario.allUsuaris()
                if (llistaUsuari.isEmpty()) {
                     call.respondText("No hi ha usuaris", status = HttpStatusCode.NotFound)
                } else {
                     call.respond(llistaUsuari)
                }
           }
            get("({id})") {
                val id = call.parameters["id"]?: return@get call.respondText(
                    "No existeix cap usuari amb la id proporcionada",
                    status = HttpStatusCode.BadRequest
                )
                val usuari = daoUsuario.usuario(id.toInt())?: return@get call.respondText(
                    "No existeix cap usuari amb la id $id proporcionada",
                    status = HttpStatusCode.NotFound
                )
                call.respond(usuari)
            }
            post {
                val usuari = call.receive<Usuari>()
                val usuariCreat = daoUsuario.addNewUsuario(usuari.usuari_nom, usuari.usuari_contra)
                if (usuariCreat == null) {
                    call.respondText("No s'ha pogut crear l'usuari", status = HttpStatusCode.BadRequest)
                } else {
                    call.respond(usuariCreat)
                }
            }
            delete("({id})") {
                val id = call.parameters["id"]?: return@delete call.respond(HttpStatusCode.BadRequest)
                val usuari = daoUsuario.usuario(id.toInt())?: return@delete call.respondText (
                    "No existeix cap usuari amb la id $id proporcionada",
                    status = HttpStatusCode.NotFound
                )
                if (daoUsuario.deleteUsuario(id.toInt())) {
                    call.respondText("Usuari eliminat correctament", status = HttpStatusCode.Accepted)
                } else {
                    call.respondText("No s'ha pogut eliminar l'usuari", status = HttpStatusCode.BadRequest)
                }
            }
        }
    }
}
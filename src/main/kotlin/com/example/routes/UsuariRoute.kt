package com.example.routes

import com.example.dao.daoUsuario
import com.example.model.Usuari
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.SerializationException

fun Routing.usuariRouting() {
    authenticate("myAuth") {
        route("/usuaris") {
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
                val usuariCreat = daoUsuario.addNewUsuario(usuari.usuari_nom, usuari.usuari_dni,usuari.usuari_contra)

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

            /*
            put("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid postId")
                } else {
                    val usuari = try {
                        call.receive<Usuari>()
                    } catch (e: SerializationException) {
                        call.respond(HttpStatusCode.BadRequest, "Invalid post data")
                        return@put
                    }
                    val updatedPost = daoUsuario.updateUsuario(
                        usu_id = usuari.usuari_id,
                        usu_nom = usuari.usuari_nom,
                        usu_dni = usuari.usuari_dni,
                        usu_adreça = usuari.usuari_adreça,
                        usu_telefon = usuari.usuari_telefon,
                        usu_contacte_emergencia = usuari.usuari_contacte_emergencia,
                        usu_imatge = usuari.usuari_imatge
                    )
                    if (updatedPost) {
                        call.respond(HttpStatusCode.OK, "User updated successfully")
                    } else {
                        call.respond(HttpStatusCode.InternalServerError, "Failed to update User")
                    }
                }
            }

            delete("/{id}") {
                val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
                daoUsuario.deleteUsuario(id.toInt())
                call.respondText("User eliminado", status = HttpStatusCode.Accepted)
            }

            delete("/{id}") {
                val postId = call.parameters["id"]?.toIntOrNull()
                if (postId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid Id")
                } else {
                    val deletedPost = daoUsuario.deleteUsuario(postId)
                    if (deletedPost) {
                        call.respond(HttpStatusCode.OK, "User deleted successfully")
                    } else {
                        call.respond(HttpStatusCode.InternalServerError, "Failed to delete user")
                    }
                }
            }

             */
        }
    }
}
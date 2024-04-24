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
    route("/usuaris") {
        get {
            val llistaUsuari = daoUsuario.allUsuaris()
            if (llistaUsuari.isNotEmpty()) {
                call.respond(llistaUsuari)
            } else {
                call.respondText("No s'han trobat usuaris")
            }
        }
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
        //post {
        //  val usuari = call.receive<Usuari>()
        //  val usuariCreat = daoUsuario.addNewUsuario(usuari.usuari_nom, usuari.usuari_dni, usuari.usuari_adreça, usuari.usuari_telefon, usuari.usuari_contacte_emergencia, usuari.usuari_imatge, usuari.usuari_contra)
        //
        //  if (usuariCreat == null) {
        //  call.respondText("No s'ha pogut crear l'usuari", status = HttpStatusCode.BadRequest)
        //  } else {
        //     call.respond(usuariCreat)
        //    }
        //  }

        //Ruta per eliminar un usuari amb el seu dni
        delete("/{usuari_dni}") {
            val usuari_dni = call.parameters["usuari_dni"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (daoUsuario.deleteUsuario(usuari_dni)) {
                call.respondText("Usuari eliminat correctament", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("No s'ha pogut eliminar l'usuari", status = HttpStatusCode.InternalServerError)
            }
        }

        //put("/{id}") {
        //    val id = call.parameters["id"]?.toIntOrNull()
        //    if (id == null) {
        //        call.respond(HttpStatusCode.BadRequest, "Invalid Id")
        //    } else {
        //        val usuari = try {
        //            call.receive<Usuari>()
        //        } catch (e: SerializationException) {
        //            call.respond(HttpStatusCode.BadRequest, "Invalid data")
        //            return@put
        //        }
                //val updatedPost = daoUsuario.updateUsuario(
                //    usu_id = id,
                //    usu_nom = usuari.usuari_nom,
                //    usu_dni = usuari.usuari_dni,
                //    usu_adreça = usuari.usuari_adreça,
                //    usu_telefon = usuari.usuari_telefon,
                //    usu_contacte_emergencia = usuari.usuari_contacte_emergencia,
                //    usu_imatge = usuari.usuari_imatge
                //)
                //if (updatedPost) {
                //    call.respond(HttpStatusCode.OK, "Usuari actualitzat correctament")
                //} else {
                //    call.respond(HttpStatusCode.InternalServerError, "Problemes per actualitzar l'usuari")
                //}
        //    }
        //}
    }
}
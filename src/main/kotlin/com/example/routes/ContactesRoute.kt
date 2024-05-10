package com.example.routes

import com.example.dao.DAOContacte
import com.example.dao.DAOPublicacions
import com.example.model.Contacte
import com.example.model.Publicacions
import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Routing.contacteRouting(daoContacte: DAOContacte) {
    route("/contactes") {
        get {
            val contactes = daoContacte.allContactes()
            if (contactes.isNotEmpty()) {
                call.respond(contactes)
            } else {
                call.respondText("No s'han trobat cap contacte", status = HttpStatusCode.OK)
            }
        }


        post {
            val data = call.receiveMultipart()
            var contactes: Contacte? = null
            val gson = Gson()

            var contacteNom = ""
            var telefon = 0
            var usuariId = 0


            data.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        if (part.name == "post_data") {
                            contactes = gson.fromJson(part.value, Contacte::class.java)
                        } else {
                            when (part.name) {
                                "contacte_nom" -> contacteNom = part.value
                                "contacte_telefon" -> telefon = part.value.toInt()
                                "usuari_id" -> usuariId = part.value.toInt()
                            }
                        }
                    }

                    else -> {}
                }
            }
            contactes = gson.fromJson(
                """{"contacte_nom":${contacteNom},"contacte_telefon":${telefon}, "usuari_id":${usuariId}}""",
                Contacte::class.java
            )

            val contacteToPost = contactes?.let { _ ->
                daoContacte.addNewContacte(
                    contactes!!.contacte_nom,
                    contactes!!.contacte_telefon,
                    contactes!!.usuari_id
                )
            }
            call.respondRedirect("/contactes/${contacteToPost?.contacte_id}")

        }

    }

}

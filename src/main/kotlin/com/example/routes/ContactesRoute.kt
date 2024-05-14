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

/**
 * Defineix les rutes per a l'endpoint /contactes, incloent-hi operacions per obtenir i crear contactes.
 *
 * @param daoContacte L'objecte DAO per a gestionar les operacions de base de dades relacionades amb els contactes.
 */
fun Routing.contacteRouting(daoContacte: DAOContacte) {
    route("/contactes") {

        /**
         * Gestiona les peticions GET per obtenir tots els contactes.
         * Respon amb la llista de tots els contactes o un missatge si no se'n troben.
         */
        get {
            val contactes = daoContacte.allContactes()
            if (contactes.isNotEmpty()) {
                call.respond(contactes)
            } else {
                call.respondText("No s'han trobat cap contacte", status = HttpStatusCode.OK)
            }
        }

        /**
         * Gestiona les peticions POST per crear un nou contacte.
         * Accepta dades de formulari multipart, les processa i respon amb una redirecció a la URL del nou contacte.
         */
        post {
            // Rebem les dades del formulari multipart.
            val data = call.receiveMultipart()
            var contactes: Contacte? = null
            val gson = Gson()

            // Inicialitzem variables per emmagatzemar les dades del contacte.
            var contacteNom = ""
            var telefon = 0
            var usuariId = 0

            // Iterem per cada part del formulari multipart.
            data.forEachPart { part ->
                when (part) {
                    // Si la part és un element de formulari (FormItem).
                    is PartData.FormItem -> {
                        if (part.name == "post_data") {
                            // Si el nom de l'element és "post_data", deserialitzem el contacte des del JSON.
                            contactes = gson.fromJson(part.value, Contacte::class.java)
                        } else {
                            // Si el nom de l'element és un altre, assignem el valor a la variable corresponent.
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

            // Creem un objecte Contacte a partir de les dades rebudes.
            contactes = gson.fromJson(
                """{"contacte_nom":${contacteNom},"contacte_telefon":${telefon}, "usuari_id":${usuariId}}""",
                Contacte::class.java
            )

            // Afegim el nou contacte a la base de dades.
            val contacteToPost = contactes?.let { _ ->
                daoContacte.addNewContacte(
                    contactes!!.contacte_nom,
                    contactes!!.contacte_telefon,
                    contactes!!.usuari_id
                )
            }

            // Redirigim a la URL del nou contacte.
            call.respondRedirect("/contactes/${contacteToPost?.contacte_id}")
        }
    }
}

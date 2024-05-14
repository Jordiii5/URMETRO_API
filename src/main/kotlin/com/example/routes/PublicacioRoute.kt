package com.example.routes

import com.example.dao.DAOPublicacions
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
 * Defineix les rutes per a l'endpoint /posts, incloent-hi diverses operacions CRUD.
 *
 * @param daoPublicacions L'objecte DAO per a gestionar les operacions de base de dades relacionades amb les publicacions.
 */
fun Routing.publicacioRouting(daoPublicacions: DAOPublicacions) {
    route("/posts"){

        /**
         * Gestiona les peticions GET per obtenir totes les publicacions.
         * Respon amb la llista de totes les publicacions o un missatge si no se'n troben.
         */
        get {
            val publicacions = daoPublicacions.allPublicacions()
            if (publicacions.isNotEmpty()) {
                call.respond(publicacions)
            } else {
                call.respondText("No s'han trobat cap publicacions", status = HttpStatusCode.OK)
            }
        }

        /**
         * Gestiona les peticions GET per obtenir publicacions per títol.
         * Respon amb les publicacions coincidents o un missatge d'error si falta el títol.
         */
        get("/{tittle?}") {
            val name = call.parameters["tittle"] ?: return@get call.respondText(
                "Missing title",
                status = HttpStatusCode.BadRequest
            )
            val publicacions = daoPublicacions.allPublicacions()

            val publicacionsRespond = mutableListOf<Publicacions>()
            for (i in publicacions) {
                if (name in i.publicacio_peu_foto) {
                    publicacionsRespond.add(i)
                }
            }
        }

        /**
         * Gestiona les peticions GET per obtenir una publicació específica pel seu ID.
         * Respon amb la publicació o un missatge d'error si l'ID no és vàlid o no es troba.
         */
        get("/{publicacio_id}") {
            val publicacioId = call.parameters["publicacio_id"]?.toIntOrNull()
            if (publicacioId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid publicacioId")
            } else {
                val publicacio = daoPublicacions.publicacio(publicacioId)
                if (publicacio == null) {
                    call.respond(HttpStatusCode.NotFound, "Publicació no trobada")
                } else {
                    call.respond(publicacio)
                }
            }
        }

        /**
         * Gestiona les peticions POST per crear una nova publicació.
         * Accepta dades de formulari multipart, les processa i respon amb una redirecció a la URL de la nova publicació.
         */
        post {
            // Rebem les dades del formulari multipart.
            val data = call.receiveMultipart()
            var publiccions: Publicacions? = null
            val gson = Gson()

            // Inicialitzem variables per emmagatzemar les dades de la publicació.
            var fileName = ""
            var peuDeFoto = ""
            var likes = 0
            var usuariId = 0

            // Iterem per cada part del formulari multipart.
            data.forEachPart { part ->
                when (part) {
                    // Si la part és un element de formulari (FormItem).
                    is PartData.FormItem -> {
                        if (part.name == "post_data") {
                            // Si el nom de l'element és "post_data", deserialitzem la publicació des del JSON.
                            publiccions = gson.fromJson(part.value, Publicacions::class.java)
                        } else {
                            // Si el nom de l'element és un altre, assignem el valor a la variable corresponent.
                            when (part.name) {
                                "publicacio_peu_foto" -> peuDeFoto = part.value
                                "publicacio_likes" -> likes = part.value.toInt()
                                "usuari_id" -> usuariId = part.value.toInt()
                            }
                        }
                    }
                    // Si la part és un fitxer (FileItem).
                    is PartData.FileItem -> {
                        fileName = part.originalFileName as String
                        var fileBytes = part.streamProvider().readBytes()
                        // Guardem el fitxer a la ubicació especificada.
                        File("./src/main/resources/imagenes/$fileName").writeBytes(fileBytes)
                    }


                    else -> {}
                }
            }

            // Creem un objecte Publicacions a partir de les dades rebudes.
            publiccions = gson.fromJson(
                """{"publicacio_peu_foto":${peuDeFoto},"publicacio_likes":${likes}, "usuari_id":${usuariId}}""",
                Publicacions::class.java
            )

            // Afegim la nova publicació a la base de dades.
            val publicacioToPost = publiccions?.let { _ ->
                daoPublicacions.addNewPublicacio(
                    fileName,
                    publiccions!!.publicacio_peu_foto,
                    publiccions!!.publicacio_likes,
                    publiccions!!.usuari_id
                )
            }

            // Redirigim a la URL de la nova publicació.
            call.respondRedirect("/posts/${publicacioToPost?.publicacio_id}")


        }

        /**
         * Gestiona les peticions DELETE per eliminar una publicació pel seu ID.
         * Respon amb un missatge de confirmació.
         */
        delete("/{postId}") {
            val id = call.parameters["postId"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            daoPublicacions.deletePublicacio(id.toInt())
            call.respondText("Publicació de foto eliminada", status = HttpStatusCode.Accepted)
        }

        /**
         * Gestiona les peticions GET per obtenir una imatge pel seu nom del directori /images.
         * Respon amb el fitxer d'imatge o un missatge d'error si no es troba.
         */
        get("/imagenes/{imageName}") {
            val imageName = call.parameters["imageName"]
            println(imageName)
            val file = File("./images/$imageName")
            println(file)
            if (file.exists()) {
                call.respondFile(File("./images/$imageName"))
            } else {
                call.respondText("Image not found", status = HttpStatusCode.NotFound)
            }
        }

        /**
         * Gestiona les peticions GET per obtenir una imatge pel seu nom del directori /src/main/resources/imagenes.
         * Respon amb el fitxer d'imatge o un missatge d'error si no es troba.
         */
        get("/imagenespost/{imageName}") {
            val imageName = call.parameters["imageName"]
            var file = File("./src/main/resources/imagenes/$imageName")
            if (file.exists()) {
                call.respondFile(File("./src/main/resources/imagenes/$imageName"))
            } else {
                call.respondText("Image not found", status = HttpStatusCode.NotFound)
            }
        }
    }
}

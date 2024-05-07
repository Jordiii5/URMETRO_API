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

fun Routing.publicacioRouting(daoPublicacions: DAOPublicacions) {
    route("/posts"){
        get {
            val publicacions = daoPublicacions.allPublicacions()
            if (publicacions.isNotEmpty()) {
                call.respond(publicacions)
            } else {
                call.respondText("No s'han trobat cap publicacions", status = HttpStatusCode.OK)
            }
        }


        get("/{tittle?}") {
            val name = call.parameters["tittle"] ?: return@get call.respondText(
                "Missing title",
                status = HttpStatusCode.BadRequest
            )
            val publicacions = daoPublicacions.allPublicacions() ?: return@get call.respondText(
                "No hi ha publicacions amb el nom $name",
                status = HttpStatusCode.NotFound
            )
            val publicacionsRespond = mutableListOf<Publicacions>()
            for (i in publicacions) {
                if (name in i.publicacio_peu_foto) {
                    publicacionsRespond.add(i)
                }
            }
        }


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

        post {
            val data = call.receiveMultipart()
            var publiccions: Publicacions? = null
            val gson = Gson()

            var fileName = ""
            var peuDeFoto = ""
            var usuariId = 0


            data.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        if (part.name == "post_data") {
                            publiccions = gson.fromJson(part.value, Publicacions::class.java)
                        } else {
                            when (part.name) {
                                "publicacio_peu_foto" -> peuDeFoto = part.value
                                "usuari_id" -> usuariId = part.value.toInt()
                            }
                        }
                    }
                    is PartData.FileItem -> {
                        fileName = part.originalFileName as String
                        var fileBytes = part.streamProvider().readBytes()
                        File("./src/main/resources/imagenes/$fileName").writeBytes(fileBytes)
                    }


                    else -> {}
                }
            }
            publiccions = gson.fromJson(
                """{publicacio_peu_foto":${peuDeFoto}, "usuari_id":${usuariId}}""",
                Publicacions::class.java
            )


            val publicacioToPost = publiccions?.let { _ ->
                daoPublicacions.addNewPublicacio(
                    fileName,
                    publiccions!!.publicacio_peu_foto,
                    publiccions!!.usuari_id
                )
            }
            call.respondRedirect("/posts/${publicacioToPost?.publicacio_id}")


        }


        delete("/{postId}") {
            val id = call.parameters["postId"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            daoPublicacions.deletePublicacio(id.toInt())
            call.respondText("Publicació de foto eliminada", status = HttpStatusCode.Accepted)
        }

/*
        delete("/{postId}") {
            val postId = call.parameters["postId"]?.toIntOrNull()
            if (postId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid postId")
            } else {
                val deletedPost = daoPublicacions.deletePublicacio(postId)
                if (deletedPost) {
                    call.respond(HttpStatusCode.OK, "Post deleted successfully")
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "Failed to delete post")
                }
            }
        }

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

/*
    route("/posts") {

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

 */


}

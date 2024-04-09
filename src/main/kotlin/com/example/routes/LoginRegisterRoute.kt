package com.example.routes.sdsd

import com.example.dao.daoUsuario
import com.example.model.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Routing.loginRegisterRouting() {
    route("user") {
        post("/register") {
            val user = call.receive<Usuari>()
            uploadUser()
            if (userTable.contains(user.usuari_dni)) {
                call.respondText("Aquest usuari ja existeix", status = HttpStatusCode.Conflict)
                return@post
            } else {
                userTable[user.usuari_dni] = getMd5Digest("${user.usuari_dni}:$myRealm:${user.usuari_contra}")
                daoUsuario.addNewUsuario(user.usuari_nom, user.usuari_dni, user.usuari_adreça, user.usuari_telefon, user.usuari_contacte_emergencia, user.usuari_imatge, user.usuari_contra)
                userTable.put(user.usuari_dni, getMd5Digest("${user.usuari_dni}:$myRealm:${user.usuari_contra}"))
                call.respondText("Usuari registrat amb exit", status = HttpStatusCode.Created)
            }
        }
        post("/login") {
            val user = call.receive<Usuari>()
            userTable = uploadUser()
            val userHidden = getMd5Digest("${user.usuari_dni}:$myRealm:${user.usuari_contra}")
            if (userTable.contains(user.usuari_dni) && userTable[user.usuari_dni]?.contentEquals(userHidden) == true) {
                call.respondText("Usuari autenticat", status = HttpStatusCode.OK)
                return@post
            } else {
                call.respondText("Usuari o contrasenya incorrectes", status = HttpStatusCode.Unauthorized)
            }
        }
    }
}
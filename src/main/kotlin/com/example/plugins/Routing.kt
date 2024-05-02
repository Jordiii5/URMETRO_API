package com.example.plugins

import com.example.dao.DAOPublicacionsImpl
import com.example.routes.publicacioRouting
import com.example.routes.sdsd.loginRegisterRouting
import com.example.routes.usuariRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(daoPublicacions: DAOPublicacionsImpl) {
    routing {
        loginRegisterRouting()
        usuariRouting()
        publicacioRouting(daoPublicacions)
    }
}

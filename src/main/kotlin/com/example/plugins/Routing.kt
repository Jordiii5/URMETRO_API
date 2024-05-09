package com.example.plugins

import com.example.dao.DAOContacteImpl
import com.example.dao.DAOPublicacionsImpl
import com.example.routes.contacteRouting
import com.example.routes.publicacioRouting
import com.example.routes.sdsd.loginRegisterRouting
import com.example.routes.usuariRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(daoPublicacions: DAOPublicacionsImpl, daoContacte: DAOContacteImpl) {
    routing {
        loginRegisterRouting()
        usuariRouting()
        publicacioRouting(daoPublicacions)
        contacteRouting(daoContacte)
    }
}

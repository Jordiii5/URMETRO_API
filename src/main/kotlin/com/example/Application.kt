package com.example

import com.example.dao.DAOPublicacionsImpl
import com.example.dao.DatabaseFactory
import com.example.plugins.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "192.168.1.68", module = Application::module)
        .start(wait = true)
}
//
//172.23.6.130
//192.168.56.1
fun Application.module() {
    DatabaseFactory.init()
    configureSecurity()
    configureSerialization()
    val daoPublicacions = DAOPublicacionsImpl()
    configureRouting(daoPublicacions)
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Delete)
        allowHeader("Content-Type")
        allowHeader("Authorization")
        anyHost()
    }
}

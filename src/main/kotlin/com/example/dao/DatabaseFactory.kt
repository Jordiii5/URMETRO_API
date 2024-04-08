package com.example.dao

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object DatabaseFactory {

    fun init() {
        val driverClassName = "org.postgresql.Driver"
        val url = "jdbc:postgresql://batyr.db.elephantsql.com:5432/hcgpwgwd"
        val user = "hcgpwgwd"
        val password = "S3kF3IJVlvBI09A8LiyEDXrR3DAIhtPR"
        Database.connect(url, driver = driverClassName, user = user, password = password)
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T {
        return newSuspendedTransaction(Dispatchers.IO) {
            block()
        }
    }
}


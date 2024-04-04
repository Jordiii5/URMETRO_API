package com.example.dao

import com.example.model.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction


object DatabaseFactory {

    fun init(){
        val driverClassName = "org.postgresql.Driver"
        val url = "postgres://hcgpwgwd:S3kF3IJVlvBI09A8LiyEDXrR3DAIhtPR@batyr.db.elephantsql.com/hcgpwgwd"
        val user = "hcgpwgwd"
        val password = "S3kF3IJVlvBI09A8LiyEDXrR3DAIhtPR"
        Database.connect(url, driverClassName, user, password)

    }

    suspend fun <T> dbQuery(block: suspend  () -> T): T {
        return newSuspendedTransaction(Dispatchers.IO) {
            block()
        }
    }
}
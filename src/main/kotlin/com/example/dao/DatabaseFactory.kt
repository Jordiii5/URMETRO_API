package com.example.dao

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

/**
 * Objecte que gestiona la configuració i la connexió a la base de dades.
 */
object DatabaseFactory {

    /**
     * Inicialitza la connexió a la base de dades.
     * Configura els paràmetres de connexió per a la base de dades PostgreSQL.
     */
    fun init() {
        val driverClassName = "org.postgresql.Driver"
        val url = "jdbc:postgresql://batyr.db.elephantsql.com:5432/hcgpwgwd"
        val user = "hcgpwgwd"
        val password = "S3kF3IJVlvBI09A8LiyEDXrR3DAIhtPR"
        Database.connect(url, driver = driverClassName, user = user, password = password)
    }

    /**
     * Executa una consulta a la base de dades dins d'una transacció suspesa.
     *
     * @param block El bloc de codi a executar dins la transacció.
     * @return El resultat de l'execució del bloc de codi.
     */
    suspend fun <T> dbQuery(block: suspend () -> T): T {
        return newSuspendedTransaction(Dispatchers.IO) {
            block()
        }
    }
}


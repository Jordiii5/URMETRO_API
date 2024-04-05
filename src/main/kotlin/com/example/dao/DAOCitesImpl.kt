package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.model.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class DAOCitesImpl : DAOCites{

    private fun resultToRowCita (row: ResultRow) = Cita(
        cites_id = row[Cites.cites_id],
        cites_nom = row[Cites.cites_nom],
        cites_comentaris = row[Cites.cites_comentaris],
        cites_dia = row[Cites.cites_dia],
        cites_hora = row[Cites.cites_hora],
        usuari_id = row[Cites.usuari_id],
    )

    override suspend fun allCites(): List<Cita> = dbQuery{
        Cites.selectAll().map(::resultToRowCita)
    }

    override suspend fun cites(cites_id: Int): Cita? = dbQuery{
        Cites
            .select { Cites.cites_id eq cites_id}
            .map(::resultToRowCita)
            .singleOrNull()
    }

    override suspend fun citesNom(cites_nom: String): Cita? = dbQuery{
        Cites
            .select { Cites.cites_nom eq cites_nom}
            .map(::resultToRowCita)
            .singleOrNull()
    }

    override suspend fun addNewcites(
        cites_nom: String,
        cites_comentaris: String
    ): Cita? = dbQuery{
        val insertStatement = Cites.insert {
            it[Cites.cites_nom] = cites_nom
            it[Cites.cites_comentaris] = cites_comentaris
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultToRowCita)
    }

}
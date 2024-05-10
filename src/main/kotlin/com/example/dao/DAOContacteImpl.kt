package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.model.Contacte
import com.example.model.Contactes
import com.example.model.Usuaris
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class DAOContacteImpl : DAOContacte{
    private fun resultToRowContacte (row: ResultRow) = Contacte(
        usuari_id = row[Contactes.usuari_id],
        contacte_id = row[Contactes.contacte_id],
        contacte_nom = row[Contactes.contacte_nom],
        contacte_telefon = row[Contactes.contacte_telefon]
    )

    override suspend fun allContactes(): List<Contacte> = dbQuery{
        Contactes.selectAll().map(::resultToRowContacte)
    }

    override suspend fun contacte(contacte_id: Int): Contacte? = dbQuery{
        Contactes
            .select { Contactes.contacte_id eq contacte_id }
            .map(::resultToRowContacte)
            .singleOrNull()
    }

    override suspend fun contacteNom(contacte_nom: String): Contacte? = dbQuery{
        Contactes
            .select { Contactes.contacte_nom eq contacte_nom }
            .map(::resultToRowContacte)
            .singleOrNull()
    }

    override suspend fun addNewContacte(contacte_nom: String, contacte_telefon: Int, usuari_id: Int): Contacte? = dbQuery{
        val insertStatement = Contactes.insert {
            it[Contactes.contacte_nom] = contacte_nom
            it[Contactes.contacte_telefon] = contacte_telefon
            it[Contactes.usuari_id] = usuari_id
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultToRowContacte)
    }

}
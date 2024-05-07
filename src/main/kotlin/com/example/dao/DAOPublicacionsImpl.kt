package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.model.Publicaciones
import com.example.model.Publicacions
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOPublicacionsImpl: DAOPublicacions {
    private fun resultToRowPublicacions (row: ResultRow) = Publicacions(
        publicacio_id = row[Publicaciones.publicacio_id],
        publicacio_foto = row[Publicaciones.publicacio_foto],
        publicacio_peu_foto = row[Publicaciones.publicacio_peu_foto],
        publicacio_likes = row[Publicaciones.publicacio_likes],
        usuari_id = row[Publicaciones.usuari_id]
    )

    override suspend fun allPublicacions(): List<Publicacions> = dbQuery {
        Publicaciones.selectAll().map(::resultToRowPublicacions)
    }

    override suspend fun publicacio(publicacio_id: Int): Publicacions? = dbQuery{
        Publicaciones
            .select { Publicaciones.publicacio_id eq publicacio_id }
            .map(::resultToRowPublicacions)
            .singleOrNull()
    }

    override suspend fun addNewPublicacio(
        publicacio_foto: String,
        publicacio_peu_foto: String,
        usuari_id: Int
    ): Publicacions? = dbQuery {
        // Verifica si publicacio_peu_foto es nulo, en cuyo caso establece un valor predeterminado
        val peuFoto = publicacio_peu_foto ?: "Sin descripción"

        // Inserta una nueva fila en la tabla Publicaciones
        val insertStatement = Publicaciones.insert {
            it[Publicaciones.publicacio_foto] = publicacio_foto
            it[Publicaciones.publicacio_peu_foto] = peuFoto
            it[Publicaciones.usuari_id] = usuari_id
        }

        // Recupera y devuelve el resultado de la inserción
        insertStatement.resultedValues?.singleOrNull()?.let(::resultToRowPublicacions)
    }



    override suspend fun deletePublicacio(publicacio_id: Int): Boolean = dbQuery {
        Publicaciones.deleteWhere { Publicaciones.publicacio_id eq publicacio_id } > 0
    }

    override suspend fun likesPublicacio(publicacio_likes: Int): Publicacions? = dbQuery{
        Publicaciones
            .select { Publicaciones.publicacio_likes eq publicacio_likes }
            .map(::resultToRowPublicacions)
            .singleOrNull()
    }
}



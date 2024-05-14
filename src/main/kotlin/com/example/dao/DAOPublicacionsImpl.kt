package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.model.Publicaciones
import com.example.model.Publicacions
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

/**
 * Implementació de la interfície DAOPublicacions per a gestionar operacions de base de dades relacionades amb les publicacions.
 */
class DAOPublicacionsImpl: DAOPublicacions {

    /**
     * Converteix un objecte ResultRow a un objecte Publicacions.
     *
     * @param row La fila de resultats a convertir.
     * @return L'objecte Publicacions corresponent.
     */
    private fun resultToRowPublicacions (row: ResultRow) = Publicacions(
        publicacio_id = row[Publicaciones.publicacio_id],
        publicacio_foto = row[Publicaciones.publicacio_foto],
        publicacio_peu_foto = row[Publicaciones.publicacio_peu_foto],
        publicacio_likes = row[Publicaciones.publicacio_likes],
        usuari_id = row[Publicaciones.usuari_id]
    )

    /**
     * Retorna una llista de totes les publicacions.
     *
     * @return Una llista d'objectes Publicacions.
     */
    override suspend fun allPublicacions(): List<Publicacions> = dbQuery {
        Publicaciones.selectAll().map(::resultToRowPublicacions)
    }

    /**
     * Retorna una publicació per ID.
     *
     * @param publicacio_id L'ID de la publicació a buscar.
     * @return L'objecte Publicacions si es troba, altrament null.
     */
    override suspend fun publicacio(publicacio_id: Int): Publicacions? = dbQuery{
        Publicaciones
            .select { Publicaciones.publicacio_id eq publicacio_id }
            .map(::resultToRowPublicacions)
            .singleOrNull()
    }

    /**
     * Afegeix una nova publicació a la base de dades.
     *
     * @param publicacio_foto La URL o camí de la foto de la publicació.
     * @param publicacio_peu_foto El peu de foto de la publicació.
     * @param publicacio_likes El nombre de "m'agrada" de la publicació.
     * @param usuari_id L'ID de l'usuari que ha creat la publicació.
     * @return L'objecte Publicacions creat, o null si la inserció falla.
     */
    override suspend fun addNewPublicacio(
        publicacio_foto: String,
        publicacio_peu_foto: String,
        publicacio_likes: Int,
        usuari_id: Int
    ): Publicacions? = dbQuery {
        val insertStatement = Publicaciones.insert {
            it[Publicaciones.publicacio_foto] = publicacio_foto
            it[Publicaciones.publicacio_peu_foto] = publicacio_peu_foto
            it[Publicaciones.publicacio_likes] = publicacio_likes
            it[Publicaciones.usuari_id] = usuari_id
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultToRowPublicacions)
    }

    /**
     * Elimina una publicació de la base de dades per ID.
     *
     * @param publicacio_id L'ID de la publicació a eliminar.
     * @return Cert si s'ha eliminat correctament, altrament fals.
     */
    override suspend fun deletePublicacio(publicacio_id: Int): Boolean = dbQuery {
        Publicaciones.deleteWhere { Publicaciones.publicacio_id eq publicacio_id } > 0
    }

    /**
     * Retorna una publicació per nombre de "m'agrada".
     *
     * @param publicacio_likes El nombre de "m'agrada" de la publicació a buscar.
     * @return L'objecte Publicacions si es troba, altrament null.
     */
    override suspend fun likesPublicacio(publicacio_likes: Int): Publicacions? = dbQuery{
        Publicaciones
            .select { Publicaciones.publicacio_likes eq publicacio_likes }
            .map(::resultToRowPublicacions)
            .singleOrNull()
    }
}



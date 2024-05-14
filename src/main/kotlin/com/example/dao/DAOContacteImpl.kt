package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.model.Contacte
import com.example.model.Contactes
import com.example.model.Usuaris
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

/**
 * Implementació de la interfície DAOContacte per gestionar operacions de base de dades relacionades amb els contactes.
 */
class DAOContacteImpl : DAOContacte{

    /**
     * Converteix un objecte ResultRow a un objecte Contacte.
     *
     * @param row La fila de resultats a convertir.
     * @return L'objecte Contacte corresponent.
     */
    private fun resultToRowContacte (row: ResultRow) = Contacte(
        usuari_id = row[Contactes.usuari_id],
        contacte_id = row[Contactes.contacte_id],
        contacte_nom = row[Contactes.contacte_nom],
        contacte_telefon = row[Contactes.contacte_telefon]
    )

    /**
     * Retorna una llista de tots els contactes.
     *
     * @return Una llista d'objectes Contacte.
     */
    override suspend fun allContactes(): List<Contacte> = dbQuery{
        Contactes.selectAll().map(::resultToRowContacte)
    }

    /**
     * Retorna un contacte per ID.
     *
     * @param contacte_id L'ID del contacte a buscar.
     * @return L'objecte Contacte si es troba, altrament null.
     */
    override suspend fun contacte(contacte_id: Int): Contacte? = dbQuery{
        Contactes
            .select { Contactes.contacte_id eq contacte_id }
            .map(::resultToRowContacte)
            .singleOrNull()
    }

    /**
     * Retorna un contacte per nom.
     *
     * @param contacte_nom El nom del contacte a buscar.
     * @return L'objecte Contacte si es troba, altrament null.
     */
    override suspend fun contacteNom(contacte_nom: String): Contacte? = dbQuery{
        Contactes
            .select { Contactes.contacte_nom eq contacte_nom }
            .map(::resultToRowContacte)
            .singleOrNull()
    }

    /**
     * Afegeix un nou contacte a la base de dades.
     *
     * @param contacte_nom El nom del contacte.
     * @param contacte_telefon El número de telèfon del contacte.
     * @param usuari_id L'ID de l'usuari associat amb el contacte.
     * @return L'objecte Contacte creat, o null si la inserció falla.
     */
    override suspend fun addNewContacte(contacte_nom: String, contacte_telefon: Int, usuari_id: Int): Contacte? = dbQuery{
        val insertStatement = Contactes.insert {
            it[Contactes.contacte_nom] = contacte_nom
            it[Contactes.contacte_telefon] = contacte_telefon
            it[Contactes.usuari_id] = usuari_id
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultToRowContacte)
    }

}
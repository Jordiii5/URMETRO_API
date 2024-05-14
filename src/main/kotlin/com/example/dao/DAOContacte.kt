package com.example.dao

import com.example.model.Contacte

/**
 * Interfície per a les operacions de base de dades relacionades amb els contactes.
 */
interface DAOContacte {

    /**
     * Retorna una llista de tots els contactes.
     *
     * @return Una llista d'objectes Contacte.
     */
    suspend fun allContactes(): List<Contacte>

    /**
     * Retorna un contacte per ID.
     *
     * @param contacte_id L'ID del contacte a buscar.
     * @return L'objecte Contacte si es troba, altrament null.
     */
    suspend fun contacte(contacte_id: Int): Contacte?

    /**
     * Retorna un contacte per nom.
     *
     * @param contacte_nom El nom del contacte a buscar.
     * @return L'objecte Contacte si es troba, altrament null.
     */
    suspend fun contacteNom (contacte_nom: String): Contacte?

    /**
     * Afegeix un nou contacte a la base de dades.
     *
     * @param contacte_nom El nom del contacte.
     * @param contacte_telefon El número de telèfon del contacte.
     * @param usuari_id L'ID de l'usuari associat amb el contacte.
     * @return L'objecte Contacte creat, o null si la inserció falla.
     */
    suspend fun addNewContacte(contacte_nom: String, contacte_telefon: Int, usuari_id: Int): Contacte?
}
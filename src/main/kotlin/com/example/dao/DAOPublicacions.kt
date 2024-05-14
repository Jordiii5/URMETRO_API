package com.example.dao

import com.example.model.Publicacions

/**
 * Interfície per a les operacions de base de dades relacionades amb les publicacions.
 */
interface DAOPublicacions {

    /**
     * Retorna una llista de totes les publicacions.
     *
     * @return Una llista d'objectes Publicacions.
     */
    suspend fun allPublicacions(): List<Publicacions>

    /**
     * Retorna una publicació per ID.
     *
     * @param publicacio_id L'ID de la publicació a buscar.
     * @return L'objecte Publicacions si es troba, altrament null.
     */
    suspend fun publicacio(publicacio_id: Int): Publicacions?

    /**
     * Afegeix una nova publicació a la base de dades.
     *
     * @param publicacio_foto La URL o camí de la foto de la publicació.
     * @param publicacio_peu_foto El peu de foto de la publicació.
     * @param publicacio_likes El nombre de "m'agrada" de la publicació.
     * @param usuari_id L'ID de l'usuari que ha creat la publicació.
     * @return L'objecte Publicacions creat, o null si la inserció falla.
     */
    suspend fun addNewPublicacio(publicacio_foto: String, publicacio_peu_foto: String, publicacio_likes: Int, usuari_id: Int): Publicacions?

    /**
     * Elimina una publicació de la base de dades per ID.
     *
     * @param publicacio_id L'ID de la publicació a eliminar.
     * @return Cert si s'ha eliminat correctament, altrament fals.
     */
    suspend fun deletePublicacio(publicacio_id: Int): Boolean

    /**
     * Retorna una publicació per nombre de "m'agrada".
     *
     * @param publicacio_likes El nombre de "m'agrada" de la publicació a buscar.
     * @return L'objecte Publicacions si es troba, altrament null.
     */
    suspend fun likesPublicacio(publicacio_likes: Int): Publicacions?
}
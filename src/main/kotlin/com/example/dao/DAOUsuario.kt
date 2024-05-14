package com.example.dao

import com.example.model.Usuari

/**
 * Interfície per a les operacions del data access object (DAO) d'usuaris.
 */
interface DAOUsuario {

    /**
     * Recupera una llista de tots els usuaris.
     *
     * @return Una llista d'objectes [Usuari] que representen tots els usuaris.
     */
    suspend fun allUsuaris(): List<Usuari>

    /**
     * Recupera un usuari pel seu ID.
     *
     * @param usuari_id L'ID de l'usuari.
     * @return L'objecte [Usuari] si es troba, o null si no existeix cap usuari amb l'ID donat.
     */
    suspend fun usuario(usuari_id: Int): Usuari?

    /**
     * Recupera un usuari pel seu DNI.
     *
     * @param usuari_dni El DNI de l'usuari.
     * @return L'objecte [Usuari] si es troba, o null si no existeix cap usuari amb el DNI donat.
     */
    suspend fun usuarioDni (usuari_dni: String): Usuari?

    /**
     * Afegeix un nou usuari.
     *
     * @param usuari_nom El nom de l'usuari.
     * @param usuari_dni El DNI de l'usuari.
     * @param usuari_telefon El número de telèfon de l'usuari.
     * @param usuari_contacte_emergencia El número de contacte d'emergència de l'usuari.
     * @param usuari_contra La contrasenya de l'usuari.
     * @return El nou objecte [Usuari] creat, o null si no s'ha pogut crear l'usuari.
     */
    suspend fun addNewUsuario(usuari_nom: String, usuari_dni: String, usuari_telefon: Int, usuari_contacte_emergencia: Int, usuari_contra: String): Usuari?

    /**
     * Actualitza la contrasenya d'un usuari existent.
     *
     * @param usuari_id L'ID de l'usuari.
     * @param usuari_contra La nova contrasenya de l'usuari.
     * @return Cert si la contrasenya s'ha actualitzat correctament, fals en cas contrari.
     */
    suspend fun updatePassword (usuari_id: Int, usuari_contra: String): Boolean

    /**
     * Actualitza les dades d'un usuari existent.
     *
     * @param usuari_dni El DNI de l'usuari.
     * @param usuari_nom El nou nom de l'usuari.
     * @param usuari_telefon El nou número de telèfon de l'usuari.
     * @param usuari_contacte_emergencia El nou número de contacte d'emergència de l'usuari.
     * @return Cert si les dades de l'usuari s'han actualitzat correctament, fals en cas contrari.
     */
    suspend fun updateUsuario(usuari_dni: String, usuari_nom: String, usuari_telefon: Int, usuari_contacte_emergencia: Int): Boolean

    /**
     * Elimina un usuari pel seu DNI.
     *
     * @param usuari_dni El DNI de l'usuari a eliminar.
     * @return Cert si l'usuari s'ha eliminat correctament, fals en cas contrari.
     */
    suspend fun deleteUsuario(usuari_dni: String): Boolean
}


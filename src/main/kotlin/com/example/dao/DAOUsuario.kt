package com.example.dao

import com.example.model.Usuari

interface DAOUsuario {
    suspend fun allUsuaris(): List<Usuari>
    suspend fun usuario(usuari_id: Int): Usuari?
    suspend fun usuarioDni (usuari_dni: String): Usuari?
    suspend fun addNewUsuario(usuari_nom: String, usuari_dni: String, usuari_adreça: String, usuari_telefon: Int, usuari_contacte_emergencia: Int, usuari_imatge: String, usuari_contra: String): Usuari?
    suspend fun updatePassword (usuari_id: Int, usuari_contra: String): Boolean
    suspend fun updateUsuario(usuari_id: Int, usuari_nom: String, usuari_dni: String, usuari_adreça: String, usuari_telefon: Int, usuari_contacte_emergencia: Int, usuari_imatge: String, usuari_contra: String): Boolean
    suspend fun deleteUsuario(usuari_id: Int): Boolean
}


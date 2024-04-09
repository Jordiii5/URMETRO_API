package com.example.dao

import com.example.model.Usuari

interface DAOUsuario {
    suspend fun allUsuaris(): List<Usuari>
    suspend fun usuario(usu_id: Int): Usuari?
    suspend fun usuarioDni (username: String): Usuari?
    suspend fun addNewUsuario(usu_username: String, usu_dni: String, usu_adreça: String, usu_telefon: Int, usu_contacte_emergencia: Int, usu_imatge: String, usu_password: String): Usuari?
    suspend fun updatePassword (usu_id: Int, usu_password: String): Boolean
    suspend fun updateUsuario(usu_id: Int, usu_nom: String, usu_dni: String, usu_adreça: String, usu_telefon: Int, usu_contacte_emergencia: Int, usu_imatge: String): Boolean
    suspend fun deleteUsuario(usu_id: Int): Boolean
}


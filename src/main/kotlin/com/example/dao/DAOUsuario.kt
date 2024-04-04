package com.example.dao

import com.example.model.Usuari

interface DAOUsuario {
    suspend fun allUsuaris(): List<Usuari>
    suspend fun usuario(usu_id: Int): Usuari?
    suspend fun usuarioDni (username: String): Usuari?
    suspend fun addNewUsuario(usu_username: String, usu_password: String): Usuari?
    suspend fun updatePassword (usu_id: Int, usu_password: String): Boolean
    suspend fun deleteUsuario(usu_id: Int): Boolean
}

package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.model.Usuari
import com.example.model.Usuaris
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOUsuarioImpl : DAOUsuario {
    private fun resultToRowUsuario (row: ResultRow) = Usuari(
        usuari_id = row[Usuaris.usuari_id],
        usuari_nom = row[Usuaris.usuari_nom],
        usuari_dni = row[Usuaris.usuari_dni],
        usuari_adreça = row[Usuaris.usuari_adreça],
        usuari_telefon = row[Usuaris.usuari_telefon],
        usuari_contacte_emergencia = row[Usuaris.usuari_contacte_emergencia],
        usuari_imatge = row[Usuaris.usuari_imatge],
        usuari_contra = row[Usuaris.usuari_contra]
    )

    override suspend fun allUsuaris(): List<Usuari> = dbQuery {
        Usuaris.selectAll().map(::resultToRowUsuario)
    }

    override suspend fun usuario(usuari_id: Int): Usuari? = dbQuery{
        Usuaris
            .select { Usuaris.usuari_id eq usuari_id }
            .map(::resultToRowUsuario)
            .singleOrNull()
    }

    override suspend fun usuarioDni(usuari_dni: String): Usuari? = dbQuery{
        Usuaris
            .select { Usuaris.usuari_dni eq usuari_dni }
            .map(::resultToRowUsuario)
            .singleOrNull()
    }

    override suspend fun addNewUsuario(usuari_nom: String, usuari_dni: String, usuari_contra: String): Usuari? = dbQuery {
        val insertStatement = Usuaris.insert {
            it[Usuaris.usuari_nom] = usuari_nom
            it[Usuaris.usuari_dni] = usuari_dni
            it[Usuaris.usuari_contra] = usuari_contra
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultToRowUsuario)
    }

    override suspend fun updatePassword(usuari_id: Int, usuari_contra: String): Boolean = dbQuery{
        Usuaris.update({Usuaris.usuari_id eq usuari_id}) {
            it[Usuaris.usuari_contra] = usuari_contra
        } < 0
    }

    override suspend fun updateUsuario(
        usu_id: Int,
        usu_nom: String,
        usu_dni: String,
        usu_adreça: String,
        usu_telefon: Int,
        usu_contacte_emergencia: Int,
        usu_imatge: String
    ): Boolean = dbQuery {
        Usuaris.update({Usuaris.usuari_id eq usu_id}) {
            it[usuari_nom] = usu_nom
            it[usuari_dni] = usu_dni
            it[usuari_adreça] = usu_adreça
            it[usuari_telefon] = usu_telefon
            it[usuari_contacte_emergencia] = usu_contacte_emergencia
            it[usuari_imatge] = usu_imatge
        } > 0

    }

    override suspend fun deleteUsuario(usuari_id: Int): Boolean = dbQuery{
        Usuaris.deleteWhere { Usuaris.usuari_id eq usuari_id } > 0
    }

}

val daoUsuario:DAOUsuario = DAOUsuarioImpl().apply{}

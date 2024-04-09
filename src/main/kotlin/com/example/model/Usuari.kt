package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
class Usuari (
    val usuari_id: Int = 0,
    val usuari_nom: String,
    val usuari_dni: String,
    val usuari_adreça: String,
    val usuari_telefon: Int,
    val usuari_contacte_emergencia: Int,
    val usuari_imatge: String,
    val usuari_contra: String
)
object Usuaris: Table("usuari"){
    val usuari_id = integer("usuari_id").autoIncrement()
    val usuari_nom = varchar("usuari_nom",255)
    val usuari_dni = varchar("usuari_dni",255)
    val usuari_adreça = varchar("usuari_adreça",255)
    val usuari_telefon = integer("usuari_telefon")
    val usuari_contacte_emergencia = integer("usuari_contacte_emergencia")
    val usuari_imatge = varchar("usuari_imatge", 255)
    val usuari_contra = varchar("usuari_contra", 255)

    override val primaryKey = PrimaryKey(usuari_id)
}
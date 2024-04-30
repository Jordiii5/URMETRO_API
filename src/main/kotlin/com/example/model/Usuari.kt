package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
class Usuari (
    val usuari_id: Int,
    val usuari_nom: String,
    val usuari_dni: String,
    val usuari_telefon: Int,
    val usuari_contacte_emergencia: Int,
    val usuari_contra: String
)
object Usuaris: Table("usuari"){
    val usuari_id = integer("usuari_id").autoIncrement()
    val usuari_nom = varchar("usuari_nom",255)
    val usuari_dni = varchar("usuari_dni",255)
    val usuari_telefon = integer("usuari_telefon")
    val usuari_contacte_emergencia = integer("usuari_contacte_emergencia")
    val usuari_contra = varchar("usuari_contra", 255)
}
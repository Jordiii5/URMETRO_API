package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Contacte (
    val contacte_id: Int,
    val contacte_nom: String,
    val contacte_telefon: Int,
    val usuari_id: Int
)
object Contactes : Table("contactes") {
    val contacte_id = integer("contacte_id").autoIncrement()
    val contacte_nom = varchar("contacte_nom", 255)
    val contacte_telefon = integer("contacte_telefon")
    val usuari_id = integer("usuari_id") references Usuaris.usuari_id

    override val primaryKey = PrimaryKey(contacte_id)
}
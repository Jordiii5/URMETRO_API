package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
class Cita (
    val cites_id: Int,
    val cites_nom: String,
    val cites_dia: String, // Cambiar a tipo Date si se desea
    val cites_hora: String, // Cambiar a tipo Time si se desea
    val cites_comentaris: String,
    val usuari_id: Int
)
object Cites : Table("cites") {
    val cites_id = integer("cites_id").autoIncrement()
    val cites_nom = varchar("cites_nom", 255)
    val cites_dia = varchar("cites_dia", 255)
    val cites_hora = varchar("cites_hora", 255)
    val cites_comentaris = text("cites_comentaris")
    val usuari_id = integer("usuari_id") references Usuaris.usuari_id

    override val primaryKey = PrimaryKey(cites_id)
}
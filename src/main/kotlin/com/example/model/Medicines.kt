package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
@Serializable
class Medicina (
    val medicines_id: Int,
    val medicines_nom: String,
    val medicines_dia: String, // Cambiar a tipo Date si se desea
    val medicines_hora: String, // Cambiar a tipo Time si se desea
    val medicines_comentari: String,
    val usuari_id: Int
)
object Medicines : Table("medicines") {
    val medicines_id = integer("medicines_id").autoIncrement()
    val medicines_nom = varchar("medicines_nom", 255)
    val medicines_dia = varchar("medicines_dia", 255)
    val medicines_hora = varchar("medicines_hora", 255)
    val medicines_comentari = text("medicines_comentari")
    val usuari_id = integer("usuari_id") references Usuaris.usuari_id

    override val primaryKey = PrimaryKey(medicines_id)
}

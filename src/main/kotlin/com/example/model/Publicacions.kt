package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
class Publicacions (
    val publicacio_id: Int,
    val publicacio_foto: String,
    val publicacio_peu_foto: String,
    val publicacio_likes: Int,
    val usuari_id: Int
)
object Publicaciones : Table("publicacions") {
    val publicacio_id = integer("publicacio_id").autoIncrement()
    val publicacio_foto = varchar("publicacio_foto", 255)
    val publicacio_peu_foto = text("publicacio_peu_foto")
    val publicacio_likes = integer("publicacio_likes")
    val usuari_id = integer("usuari_id") references Usuaris.usuari_id

    override val primaryKey = PrimaryKey(publicacio_id)
}
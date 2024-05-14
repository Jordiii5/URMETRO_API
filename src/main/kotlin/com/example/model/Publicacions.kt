package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

/**
 * Representa una publicació amb les seves propietats.
 *
 * @property publicacio_id L'identificador únic de la publicació.
 * @property publicacio_foto La URL o el camí de la foto de la publicació.
 * @property publicacio_peu_foto El peu de foto associat a la publicació.
 * @property publicacio_likes El nombre de "m'agrada" que ha rebut la publicació.
 * @property usuari_id L'identificador de l'usuari que ha creat la publicació.
 */
@Serializable
data class Publicacions (
    val publicacio_id: Int,
    val publicacio_foto: String,
    val publicacio_peu_foto: String,
    val publicacio_likes: Int,
    val usuari_id: Int
)

/**
 * Defineix la taula "publicacions" en la base de dades, utilitzant Exposed.
 */
object Publicaciones : Table("publicacions") {
    // Definició de les columnes de la taula "publicacions".
    val publicacio_id = integer("publicacio_id").autoIncrement() // Columna per a l'ID de la publicació, amb auto increment.
    val publicacio_foto = varchar("publicacio_foto", 255) // Columna per a la URL o el camí de la foto de la publicació.
    val publicacio_peu_foto = text("publicacio_peu_foto") // Columna per al peu de foto de la publicació.
    val publicacio_likes = integer("publicacio_likes") // Columna per al nombre de "m'agrada" de la publicació.
    val usuari_id = integer("usuari_id") references Usuaris.usuari_id // Columna per a l'ID de l'usuari, amb referència a la taula "usuaris".

    // Definició de la clau primària de la taula "publicacions".
    override val primaryKey = PrimaryKey(publicacio_id)
}
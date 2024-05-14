package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

/**
 * Representa un contacte amb les seves propietats.
 *
 * @property contacte_id L'identificador únic del contacte.
 * @property contacte_nom El nom del contacte.
 * @property contacte_telefon El número de telèfon del contacte.
 * @property usuari_id L'identificador de l'usuari associat amb el contacte.
 */
@Serializable
data class Contacte (
    val contacte_id: Int,
    val contacte_nom: String,
    val contacte_telefon: Int,
    val usuari_id: Int
)

/**
 * Defineix la taula "contactes" en la base de dades, utilitzant Exposed.
 */
object Contactes : Table("contactes") {
    // Definició de les columnes de la taula "contactes".
    val contacte_id = integer("contacte_id").autoIncrement() // Columna per a l'ID del contacte, amb auto increment.
    val contacte_nom = varchar("contacte_nom", 255) // Columna per al nom del contacte.
    val contacte_telefon = integer("contacte_telefon") // Columna per al número de telèfon del contacte.
    val usuari_id = integer("usuari_id") references Usuaris.usuari_id // Columna per a l'ID de l'usuari, amb referència a la taula "usuaris".

    // Definició de la clau primària de la taula "contactes".
    override val primaryKey = PrimaryKey(contacte_id)
}
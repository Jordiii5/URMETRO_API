package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

/**
 * Classe que representa un usuari.
 *
 * @property usuari_id L'ID de l'usuari.
 * @property usuari_nom El nom de l'usuari.
 * @property usuari_dni El DNI de l'usuari.
 * @property usuari_telefon El número de telèfon de l'usuari.
 * @property usuari_contacte_emergencia El número de contacte d'emergència de l'usuari.
 * @property usuari_contra La contrasenya de l'usuari.
 */
@Serializable
class Usuari (
    val usuari_id: Int,
    val usuari_nom: String,
    val usuari_dni: String,
    val usuari_telefon: Int,
    val usuari_contacte_emergencia: Int,
    val usuari_contra: String
)

/**
 * Objecte que representa la taula "usuari" a la base de dades.
 */
object Usuaris: Table("usuari"){
    val usuari_id = integer("usuari_id").autoIncrement()
    val usuari_nom = varchar("usuari_nom",255)
    val usuari_dni = varchar("usuari_dni",255)
    val usuari_telefon = integer("usuari_telefon")
    val usuari_contacte_emergencia = integer("usuari_contacte_emergencia")
    val usuari_contra = varchar("usuari_contra", 255)
}
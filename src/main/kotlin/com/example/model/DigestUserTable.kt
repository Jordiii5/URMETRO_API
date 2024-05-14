package com.example.model

import com.example.dao.daoUsuario
import io.ktor.server.auth.*
import java.security.MessageDigest

/**
 * Classe de dades que representa una taula d'usuaris amb un nom d'usuari i un àmbit (realm).
 *
 * @property userName El nom d'usuari.
 * @property realm L'àmbit d'accés.
 */
data class DigestUserTable(val userName: String, val realm: String) : Principal

/**
 * Genera un hash MD5 per a una cadena donada.
 *
 * @param str La cadena per la qual generar el hash MD5.
 * @return Un array de bytes que representa el hash MD5 de la cadena.
 */
fun getMd5Digest(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(Charsets.UTF_8))

/**
 * L'àmbit d'accés per a l'aplicació.
 */
val myRealm = "Access to the '/' path"

/**
 * Taula d'usuaris en memòria, inicialment amb un usuari d'administrador.
 */
var userTable: MutableMap<String, ByteArray> = mutableMapOf(
    "admin" to getMd5Digest("admin:$myRealm:password")
)

/**
 * Carrega els usuaris des de la base de dades i actualitza la taula d'usuaris en memòria.
 *
 * @return Un mapa mutable amb els DNI dels usuaris com a claus i els seus hash MD5 com a valors.
 */
suspend fun uploadUser(): MutableMap<String, ByteArray> {
    val userList = daoUsuario.allUsuaris()
    if (userList.isEmpty()) {
        return mutableMapOf() // Retorna un mapa buit si no hi ha usuaris
    }
    for (user in userList) {
        userTable[user.usuari_dni] = getMd5Digest("${user.usuari_dni}:$myRealm:${user.usuari_contra}")
    }
    return userTable
}

package com.example.dao

import com.example.model.Contacte

interface DAOContacte {
    suspend fun allContactes(): List<Contacte>
    suspend fun contacte(contacte_id: Int): Contacte?
    suspend fun contacteNom (contacte_nom: String): Contacte?
    suspend fun addNewContacte(contacte_nom: String, contacte_telefon: Int): Contacte?
}
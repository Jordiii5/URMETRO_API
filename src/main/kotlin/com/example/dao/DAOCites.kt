package com.example.dao

import com.example.model.Cita

interface DAOCites {
    suspend fun allCites():List<Cita>
    suspend fun cites(cites_id: Int): Cita?
    suspend fun citesNom (cites_nom:String): Cita?
    suspend fun addNewcites(cites_nom: String, cites_comentaris:String): Cita?
}
package com.example.dao

import com.example.model.Medicina

interface DAOMedicines {
    suspend fun allMedicines(): List<Medicina>
    suspend fun medicina(medicina_id: Int): Medicina?
    suspend fun medicinaNom(medicina_nom: String): Medicina?
    suspend fun addNewMedicina(medicina_nom: String, medicines_comentari: String): Medicina?
}
package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.model.Medicina
import com.example.model.Medicines
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class DAOMedicinesImpl : DAOMedicines{
    private fun resultToRowMedicina (row: ResultRow) = Medicina(
        medicines_id = row[Medicines.medicines_id],
        medicines_nom = row[Medicines.medicines_nom],
        medicines_dia = row[Medicines.medicines_dia],
        medicines_hora = row[Medicines.medicines_hora],
        medicines_comentari = row[Medicines.medicines_comentari],
        usuari_id = row[Medicines.usuari_id],
    )
    override suspend fun allMedicines(): List<Medicina> = dbQuery{
        Medicines.selectAll().map(::resultToRowMedicina)
    }

    override suspend fun medicina(medicina_id: Int): Medicina? = dbQuery{
        Medicines
            .select {Medicines.medicines_id eq medicina_id}
            .map(::resultToRowMedicina)
            .singleOrNull()
    }

    override suspend fun medicinaNom(medicina_nom: String): Medicina? = dbQuery{
        Medicines
            .select {Medicines.medicines_nom eq medicina_nom}
            .map(::resultToRowMedicina)
            .singleOrNull()
    }

    override suspend fun addNewMedicina(medicines_nom: String, medicines_comentari: String): Medicina? = dbQuery{
        val insertStatement = Medicines.insert {
            it[Medicines.medicines_nom] = medicines_nom
            it[Medicines.medicines_comentari] = medicines_comentari
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultToRowMedicina)
    }

}
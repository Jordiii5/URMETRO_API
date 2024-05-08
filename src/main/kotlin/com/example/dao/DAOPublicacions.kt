package com.example.dao

import com.example.model.Publicacions

interface DAOPublicacions {
    suspend fun allPublicacions(): List<Publicacions>
    suspend fun publicacio(publicacio_id: Int): Publicacions?
    suspend fun addNewPublicacio(publicacio_foto: String, publicacio_peu_foto: String, publicacio_likes: Int, usuari_id: Int): Publicacions?
    suspend fun deletePublicacio(publicacio_id: Int): Boolean
    suspend fun likesPublicacio(publicacio_likes: Int): Publicacions?
}
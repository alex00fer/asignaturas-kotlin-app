package com.example.tareafragmentosalejandrofernandez.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Profesor (
    @PrimaryKey val codigoProfesor: Int,
    val nombre: String,
    val apellido: String,
    val asigntura: String
)
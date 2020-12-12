package com.example.tareafragmentosalejandrofernandez.database

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Alumno (
    @PrimaryKey val codigoAlumno: Int,
    val nombre: String,
    val apellido: String
)
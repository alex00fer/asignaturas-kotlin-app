package com.example.tareafragmentosalejandrofernandez.database

import androidx.room.Entity

@Entity(primaryKeys = ["codigoAlumno", "asignatura"])
class AlumnoAsignaturaCrossRef(
    val codigoAlumno: Int,
    val asignatura: String
)
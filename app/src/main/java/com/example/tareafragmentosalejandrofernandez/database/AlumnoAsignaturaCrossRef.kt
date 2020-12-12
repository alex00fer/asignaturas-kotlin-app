package com.example.tareafragmentosalejandrofernandez.database

import androidx.room.Entity

@Entity(primaryKeys = ["alumnoId", "asignatura"])
class AlumnoAsignaturaCrossRef(
    val codigoAlumno: Int,
    val asignatura: String
)
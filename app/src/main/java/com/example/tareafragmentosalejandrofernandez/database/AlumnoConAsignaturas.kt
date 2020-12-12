package com.example.tareafragmentosalejandrofernandez.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class AlumnoConAsignaturas(
    @Embedded val alumno: Alumno,
    @Relation(
        parentColumn = "codigoAlumno",
        entityColumn = "asignatura",
        associateBy = Junction(AlumnoAsignaturaCrossRef::class)
    )
    val asignaturas: List<Asignatura>
)
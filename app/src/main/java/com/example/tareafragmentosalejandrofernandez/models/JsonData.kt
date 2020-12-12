package com.example.tareafragmentosalejandrofernandez.models

data class JsonData (
    val asignaturas: ArrayList<String>,
    val profesores: ArrayList<ProfesorData>,
    val alumnos: ArrayList<AlumnoData>,
)
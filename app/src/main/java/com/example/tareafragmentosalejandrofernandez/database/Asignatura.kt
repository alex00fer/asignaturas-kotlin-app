package com.example.tareafragmentosalejandrofernandez.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Asignatura (
    @PrimaryKey val asignatura: String
)
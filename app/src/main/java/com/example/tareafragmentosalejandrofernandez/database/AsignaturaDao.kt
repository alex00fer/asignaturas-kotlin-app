package com.example.tareafragmentosalejandrofernandez.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AsignaturaDao {
    @Query("SELECT * FROM asignatura")
    fun getAll(): List<Asignatura>
    @Insert
    fun insertAll(vararg asignatura: Asignatura)
}
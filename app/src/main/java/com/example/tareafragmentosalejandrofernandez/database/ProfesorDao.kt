package com.example.tareafragmentosalejandrofernandez.database

import androidx.room.*

@Dao
interface ProfesorDao {

    @Query("SELECT * FROM profesor")
    fun getAll(): List<Profesor>

    @Query("SELECT * FROM profesor WHERE asigntura LIKE :asignatura")
    fun get(asignatura: String): List<Profesor>

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg profesor: Profesor)

}
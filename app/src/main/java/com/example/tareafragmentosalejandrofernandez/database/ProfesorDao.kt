package com.example.tareafragmentosalejandrofernandez.database

import androidx.room.*

@Dao
interface ProfesorDao {

    @Query("SELECT * FROM profesor")
    fun getAll(): List<Profesor>

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg profesor: Profesor)

}
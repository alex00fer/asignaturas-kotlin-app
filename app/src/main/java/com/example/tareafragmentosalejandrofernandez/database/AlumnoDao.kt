package com.example.tareafragmentosalejandrofernandez.database

import androidx.room.*

@Dao
interface AlumnoDao {

    @Query("SELECT * FROM alumno")
    fun getAll(): List<Alumno>

    @Transaction
    @Query("SELECT * FROM alumno")
    fun getAlumnosConAsignaturas(): List<AlumnoConAsignaturas>

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg alumno: Alumno)

}
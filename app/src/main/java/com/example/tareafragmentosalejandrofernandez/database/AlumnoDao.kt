package com.example.tareafragmentosalejandrofernandez.database

import androidx.room.*

@Dao
interface AlumnoDao {

    @Query("SELECT * FROM alumno")
    fun getAll(): List<Alumno>

    @Transaction
    @Query("SELECT * FROM alumno")
    fun getAlumnosConAsignaturas(): List<AlumnoConAsignaturas>

    @Transaction
    @Query("SELECT * FROM alumno JOIN alumnoasignaturacrossref ON alumno.codigoAlumno = alumnoasignaturacrossref.codigoAlumno WHERE alumnoasignaturacrossref.asignatura LIKE :asignatura")
    fun getAlumnosConAsignatura(asignatura: String): List<AlumnoConAsignaturas>

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg alumno: Alumno)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(join: AlumnoAsignaturaCrossRef)
}
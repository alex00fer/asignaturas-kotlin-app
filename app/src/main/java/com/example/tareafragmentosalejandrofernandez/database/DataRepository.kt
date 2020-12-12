package com.example.tareafragmentosalejandrofernandez.database

import android.content.Context
import android.os.AsyncTask

class DataRepository(context: Context) {

    private val alumnoDao: AlumnoDao? = AppDatabase.getInstance(context)?.alumnoDao()
    private val profesorDao: ProfesorDao? = AppDatabase.getInstance(context)?.profesorDao()
    private val asignaturaDao: AsignaturaDao? = AppDatabase.getInstance(context)?.asignaturaDao()

    fun insert(alumno: Alumno):Int {
        if (alumnoDao != null) {
            return InsertAlumnoAsyncTask(alumnoDao).execute(alumno).get()
        }
        return -1
    }

    fun insert(prefesor: Profesor):Int {
        if (profesorDao != null) {
            return InsertProfesorAsyncTask(profesorDao).execute(prefesor).get()
        }
        return -1
    }

    fun insert(asignatura: Asignatura):Int {
        if (asignaturaDao != null) {
            return InsertAsignaturaAsyncTask(asignaturaDao).execute(asignatura).get()
        }
        return -1
    }

    fun relate(codigoAlumno: Int, asignatura: String):Int {
        if (alumnoDao != null) {
            return InsertCrossRefAsyncTask(alumnoDao).execute(
                AlumnoAsignaturaCrossRef(codigoAlumno, asignatura)).get()
        }
        return -1
    }

    private class InsertAlumnoAsyncTask(private val dao: AlumnoDao) : AsyncTask<Alumno, Void, Int>() {

        override fun doInBackground(vararg params: Alumno?): Int {
            try {
                for (param in params) {
                    if (param != null) dao.insertAll(param)
                }
                return 0
            }
            catch (exception: Exception){
                return -1
            }
        }
    }

    private class InsertProfesorAsyncTask(private val dao: ProfesorDao) : AsyncTask<Profesor, Void, Int>() {

        override fun doInBackground(vararg params: Profesor?): Int {
            try {
                for (param in params) {
                    if (param != null) dao.insertAll(param)
                }
                return 0
            }
            catch (exception: Exception){
                return -1
            }
        }
    }

    private class InsertAsignaturaAsyncTask(private val dao: AsignaturaDao) : AsyncTask<Asignatura, Void, Int>() {

        override fun doInBackground(vararg params: Asignatura?): Int {
            try {
                for (param in params) {
                    if (param != null) dao.insertAll(param)
                }
                return 0
            }
            catch (exception: Exception){
                return -1
            }
        }
    }

    private class InsertCrossRefAsyncTask(private val dao: AlumnoDao) : AsyncTask<AlumnoAsignaturaCrossRef, Void, Int>() {

        override fun doInBackground(vararg params: AlumnoAsignaturaCrossRef?): Int {
            try {
                for (param in params) {
                    if (param != null) dao.insert(param)
                }
                return 0
            }
            catch (exception: Exception){
                return -1
            }
        }
    }

}
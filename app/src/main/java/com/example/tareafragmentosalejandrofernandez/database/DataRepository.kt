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

    fun getAlumnos(): List<Alumno>{
        return GetAlumnosTask(alumnoDao!!).execute().get()
    }

    fun getAlumnosConAsignaturas(): List<AlumnoConAsignaturas>{
        return GetAlumnosAsignaturasTask(alumnoDao!!).execute().get()
    }

    fun getAlumnosConAsignatura(asignatura: String): List<AlumnoConAsignaturas>{
        return GetAlumnosAsignaturaTask(alumnoDao!!, asignatura).execute().get()
    }

    fun getProfesor(asignatura: String): List<Profesor>{
        return GetProfesorTask(profesorDao!!, asignatura).execute().get()
    }

    fun getAsignaturas(): List<String>{
        var asgs = GetAsignaturasTask(asignaturaDao!!).execute().get()
        var result = ArrayList<String>()
        for (a in asgs)
            result.add(a.asignatura)
        return result.toList()
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

    private class GetAlumnosTask(private val dao: AlumnoDao) :AsyncTask<Void, Void, List<Alumno>>(){
        override fun doInBackground(vararg params: Void?): List<Alumno> {
            return dao.getAll()
        }
    }

    private class GetProfesorTask(private val dao: ProfesorDao, private  val asignatura: String) :AsyncTask<Void, Void, List<Profesor>>(){
        override fun doInBackground(vararg params: Void?): List<Profesor> {
            return dao.get(asignatura)
        }
    }

    private class GetAsignaturasTask(private val dao: AsignaturaDao) :AsyncTask<Void, Void, List<Asignatura>>(){
        override fun doInBackground(vararg params: Void?): List<Asignatura> {
            return dao.getAll()
        }
    }

    private class GetAlumnosAsignaturasTask(private val dao: AlumnoDao) :AsyncTask<Void, Void, List<AlumnoConAsignaturas>>(){
        override fun doInBackground(vararg params: Void?): List<AlumnoConAsignaturas> {
            return dao.getAlumnosConAsignaturas()
        }
    }

    private class GetAlumnosAsignaturaTask(private val dao: AlumnoDao, private val asignatura: String) :AsyncTask<Void, Void, List<AlumnoConAsignaturas>>(){
        override fun doInBackground(vararg params: Void?): List<AlumnoConAsignaturas> {
            return dao.getAlumnosConAsignatura(asignatura)
        }
    }

}
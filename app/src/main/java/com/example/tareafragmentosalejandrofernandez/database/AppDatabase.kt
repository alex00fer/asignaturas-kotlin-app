package com.example.tareafragmentosalejandrofernandez.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Alumno::class, Asignatura::class, AlumnoAsignaturaCrossRef::class, Profesor::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alumnoDao(): AlumnoDao
    abstract fun profesorDao(): ProfesorDao
    abstract fun asignaturaDao(): AsignaturaDao

    companion object {
        private const val DATABASE_NAME = "asignaturas_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
            }
            return INSTANCE
        }

    }
}
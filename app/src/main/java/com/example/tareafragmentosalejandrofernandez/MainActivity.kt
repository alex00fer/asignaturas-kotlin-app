package com.example.tareafragmentosalejandrofernandez

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.tareafragmentosalejandrofernandez.controller.JsonLoader
import com.example.tareafragmentosalejandrofernandez.database.Alumno
import com.example.tareafragmentosalejandrofernandez.database.DataRepository
import com.example.tareafragmentosalejandrofernandez.fragments.ListaAlumnosFragment
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    var frameTop: FrameLayout? = null
    var frameBottom: FrameLayout? = null
    var listaAlumnosFragment: ListaAlumnosFragment? = null

    var dataRepository = DataRepository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        frameTop = findViewById(R.id.frameTop)
        frameBottom = findViewById(R.id.frameBottom)

        // JSON

        var jsonLoad = JsonLoader()
        var reader = BufferedReader(InputStreamReader(resources!!.openRawResource(R.raw.datos)))
        var jsonText = reader.readText()
        reader.close()
        //Toast.makeText(this, jsonText, Toast.LENGTH_SHORT).show()
        jsonLoad.load(jsonText)
        var datos = jsonLoad.data
        //Toast.makeText(this, jsonLoad.data.alumnos[0].nombre, Toast.LENGTH_SHORT).show()

        // POPULATE
        // insertar alumnos
        for (al in datos.alumnos)
            dataRepository.insert(Alumno(al.codigo, al.nombre, al.apellido))
        for (al in datos.profesores)
            dataRepository.insert(Alumno(al.codigo, al.nombre, al.apellido))


        // SPINNER
        var spinner = findViewById<Spinner>(R.id.spinnerAsignatura)
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, datos.asignaturas)
        spinner.adapter = adapterSpinner

        val activityContext = this;
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(activityContext, "Nothing selected", Toast.LENGTH_LONG).show()
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(activityContext, spinner.selectedItem.toString(), Toast.LENGTH_LONG).show()
            }
        }


        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape



        } else {

            // In portrait
            //Toast.makeText(this, "PORTRAIT", Toast.LENGTH_SHORT).show()

            listaAlumnosFragment = ListaAlumnosFragment.newInstance(datos.alumnos)
            listaAlumnosFragment!!.activityListener = activityListener

            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.frameBottom, listaAlumnosFragment!!)
            fragmentTransaction.commit()


        }
    }

    // onclick
    val activityListener = View.OnClickListener {
        /*
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frameLayoutFragment, fichaFragment!!)
            fragmentTransaction.commit()
            fragmentManager.executePendingTransactions()
            segundoFragmentActivo = true
        }
        fichaFragment!!.updateData(listaFragment!!.itemSeleccionado)*/
    }
}
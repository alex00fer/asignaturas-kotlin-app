package com.example.tareafragmentosalejandrofernandez

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tareafragmentosalejandrofernandez.controller.JsonLoader
import com.example.tareafragmentosalejandrofernandez.database.*
import com.example.tareafragmentosalejandrofernandez.fragments.AlumnoFragment
import com.example.tareafragmentosalejandrofernandez.fragments.ListaAlumnosFragment
import com.example.tareafragmentosalejandrofernandez.fragments.ProfesorFragment
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    var frameTop: FrameLayout? = null
    var frameBottom: FrameLayout? = null
    var listaAlumnosFragment: ListaAlumnosFragment? = null
    var profesorFragment: ProfesorFragment? = null
    var alumnoFragment: AlumnoFragment? = null

    var segundoFragmentActivo: Boolean = false
    var profesorCache: Profesor? = null

    lateinit var dataRepository: DataRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataRepository = DataRepository(this)

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
        for (al in datos.alumnos) {
            dataRepository.insert(Alumno(al.codigo, al.nombre, al.apellido))
            //Toast.makeText(this, al.Asignaturas.size, Toast.LENGTH_LONG).show()
            for (asignatura in al.Asignaturas)
                dataRepository.relate(al.codigo, asignatura)
        }
        // insertar profesores
        for (pr in datos.profesores)
            dataRepository.insert(Profesor(pr.codigo, pr.nombre, pr.apellido, pr.asignatura))
        // insertar asignaturas
        for (asignatura in datos.asignaturas)
            dataRepository.insert(Asignatura(asignatura))


        // GET
        var alumnosData = dataRepository.getAlumnosConAsignaturas()

        // SPINNER
        var spinner = findViewById<Spinner>(R.id.spinnerAsignatura)
        var asignaturas = dataRepository.getAsignaturas()
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, asignaturas)
        spinner.adapter = adapterSpinner

        val activityContext = this;
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(activityContext, "Nothing selected", Toast.LENGTH_LONG).show()
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //Toast.makeText(activityContext, spinner.selectedItem.toString(), Toast.LENGTH_LONG).show()

                val selected = spinner.selectedItem.toString()

                var resultProfesor = dataRepository.getProfesor(selected)
                if (resultProfesor != null &&resultProfesor.size > 0) {
                    profesorFragment!!.updateData(resultProfesor[0])
                    profesorCache = resultProfesor[0]
                }
                else
                    profesorFragment!!.updateData(null)

                var resultAlumnos = dataRepository.getAlumnosConAsignatura(selected)
                listaAlumnosFragment!!.updateData(resultAlumnos as ArrayList<AlumnoConAsignaturas>)

            }
        }

        // Preparar fragmentos
        listaAlumnosFragment = ListaAlumnosFragment.newInstance(alumnosData as ArrayList<AlumnoConAsignaturas>)
        listaAlumnosFragment!!.activityListener = activityListener
        profesorFragment = ProfesorFragment.newInstance()
        alumnoFragment = AlumnoFragment.newInstance()

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape



        } else {

            // In portrait

            //Toast.makeText(this, "PORTRAIT", Toast.LENGTH_SHORT).show()

            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.frameBottom, listaAlumnosFragment!!)
            fragmentTransaction.add(R.id.frameTop, profesorFragment!!)
            fragmentTransaction.commit()

            //Toast.makeText(this, dataRepository.getAlumnos()[0].apellido, Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {
        val orientation = resources.configuration.orientation
        if (segundoFragmentActivo && orientation == Configuration.ORIENTATION_PORTRAIT){
            reemplazarFragmentTop(profesorFragment!!)
            segundoFragmentActivo = false
            if (profesorCache != null)
                profesorFragment!!.updateData(profesorCache)
        }
        else{
            super.onBackPressed()
        }
    }

    // onclick
    val activityListener = View.OnClickListener {
        Toast.makeText(this, listaAlumnosFragment!!.itemSeleccionado!!.alumno.nombre, Toast.LENGTH_SHORT).show()

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            reemplazarFragmentTop(alumnoFragment!!)
            segundoFragmentActivo = true
        }
        alumnoFragment!!.updateData(listaAlumnosFragment!!.itemSeleccionado)

    }

    private fun reemplazarFragmentTop(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameTop, fragment)
        fragmentTransaction.commit()
        fragmentManager.executePendingTransactions()
    }
}
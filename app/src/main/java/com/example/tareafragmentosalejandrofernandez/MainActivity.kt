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
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {

    // Fragmentos
    var frameTop: FrameLayout? = null
    var frameBottom: FrameLayout? = null
    var spinner: Spinner? = null
    var listaAlumnosFragment: ListaAlumnosFragment? = null
    var profesorFragment: ProfesorFragment? = null
    var alumnoFragment: AlumnoFragment? = null
    var segundoFragmentActivo: Boolean = false

    // Instace state
    private val INSTANCE_KEY_ASIGNATURA = "asignaturaValue"
    var asignaturaCache: String? = null
    companion object {
        var profesorCache: Profesor? = null
        var alumnoCache: AlumnoConAsignaturas? = null
    }

    lateinit var dataRepository: DataRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataRepository = DataRepository(this)

        frameTop = findViewById(R.id.frameTop)
        frameBottom = findViewById(R.id.frameBottom)

        popularBaseDeDatos()

        // Get alumnos y asignaturas
        var alumnosData = dataRepository.getAlumnosConAsignaturas()
        var asignaturas = dataRepository.getAsignaturas()

        // Spinner
        spinner = findViewById<Spinner>(R.id.spinnerAsignatura)
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, asignaturas)
        spinner!!.adapter = adapterSpinner
        val activityContext = this;
        spinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(activityContext, "Nothing selected", Toast.LENGTH_LONG).show()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val selected = spinner!!.selectedItem.toString()
                asignaturaCache = selected

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
            // landscape
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frameLandLeft, listaAlumnosFragment!!)
            fragmentTransaction.replace(R.id.frameLandProfesor, profesorFragment!!)
            fragmentTransaction.replace(R.id.frameLandAlumno, alumnoFragment!!)
            fragmentTransaction.commit()

        } else {
            // portrait
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frameBottom, listaAlumnosFragment!!)
            fragmentTransaction.replace(R.id.frameTop, profesorFragment!!)
            fragmentTransaction.commit()

        }
    }

    private fun popularBaseDeDatos() {

        // Cargar JSON
        var jsonLoad = JsonLoader()
        var reader = BufferedReader(InputStreamReader(resources!!.openRawResource(R.raw.datos)))
        var jsonText = reader.readText()
        reader.close()
        jsonLoad.load(jsonText)
        var datos = jsonLoad.data

        // Popular base de datos
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
        Toast.makeText(
            this,
            listaAlumnosFragment!!.itemSeleccionado!!.alumno.nombre,
            Toast.LENGTH_SHORT
        ).show()

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            reemplazarFragmentTop(alumnoFragment!!)
            segundoFragmentActivo = true
        }
        alumnoFragment!!.updateData(listaAlumnosFragment!!.itemSeleccionado)
        alumnoCache = listaAlumnosFragment!!.itemSeleccionado

    }

    private fun reemplazarFragmentTop(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameTop, fragment)
        fragmentTransaction.commit()
        fragmentManager.executePendingTransactions()
    }

    // Mantener state
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState!!)
        var asig = savedInstanceState.getString(INSTANCE_KEY_ASIGNATURA).toString()
        if (asig != null && spinnerAsignatura != null) {
            spinner!!.setSelection((spinner!!.getAdapter() as ArrayAdapter<String>).getPosition(asig))
        }
        if (profesorCache != null && profesorFragment != null) {
            profesorFragment!!.updateData(profesorCache)
        }
        if (alumnoCache != null && alumnoFragment != null) {
            val orientation = resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                reemplazarFragmentTop(alumnoFragment!!)
                segundoFragmentActivo = true
            }
            alumnoFragment!!.updateData(alumnoCache)
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(INSTANCE_KEY_ASIGNATURA, asignaturaCache)
        super.onSaveInstanceState(outState)
    }


}
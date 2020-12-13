package com.example.tareafragmentosalejandrofernandez.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tareafragmentosalejandrofernandez.R
import com.example.tareafragmentosalejandrofernandez.adapters.AlumnoDataAdapter
import com.example.tareafragmentosalejandrofernandez.database.Alumno
import com.example.tareafragmentosalejandrofernandez.database.AlumnoConAsignaturas
import com.example.tareafragmentosalejandrofernandez.database.Profesor

class AlumnoFragment : Fragment() {

    var textViewNombre: TextView? = null
    var textViewApellido: TextView? = null
    var textViewAsignaturas: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_alumno, container, false)

        textViewNombre = v.findViewById(R.id.textViewNombreAlumno)
        textViewApellido = v.findViewById(R.id.textViewApellidoAlumno)
        textViewAsignaturas = v.findViewById(R.id.textViewAsignaturasAlumno)

        return v
    }

    fun updateData(item: AlumnoConAsignaturas?) {
        if (item!=null) {
            textViewNombre!!.text = item.alumno.nombre
            textViewApellido!!.text = item.alumno.apellido
            var textAsignaturas = ""
            for (asg in item.asignaturas) {
                textAsignaturas += asg.asignatura + " "
            }
            textViewAsignaturas!!.text = textAsignaturas
        }
        else {
            textViewNombre!!.text = "---"
            textViewApellido!!.text = "---"
            textViewAsignaturas!!.text = "---"
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AlumnoFragment().apply {}
    }
}
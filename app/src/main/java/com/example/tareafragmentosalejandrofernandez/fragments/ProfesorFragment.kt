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
import com.example.tareafragmentosalejandrofernandez.database.AlumnoConAsignaturas
import com.example.tareafragmentosalejandrofernandez.database.Profesor

class ProfesorFragment : Fragment() {

    var textViewNombre: TextView? = null
    var textViewApellido: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_profesor, container, false)

        textViewNombre = v.findViewById(R.id.textViewNombreProfesor)
        textViewApellido = v.findViewById(R.id.textViewApellidoProfesor)

        return v
    }

    fun updateData(item: Profesor?) {
        if (item!=null) {
            textViewNombre!!.text = item.nombre
            textViewApellido!!.text = item.apellido
        }
        else {
            textViewNombre!!.text = "---"
            textViewApellido!!.text = "---"
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ProfesorFragment().apply {}
    }
}
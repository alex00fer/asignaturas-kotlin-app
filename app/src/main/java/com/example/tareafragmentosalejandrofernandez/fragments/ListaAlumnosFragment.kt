package com.example.tareafragmentosalejandrofernandez.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tareafragmentosalejandrofernandez.R
import com.example.tareafragmentosalejandrofernandez.adapters.AlumnoDataAdapter
import com.example.tareafragmentosalejandrofernandez.models.AlumnoData

class ListaAlumnosFragment : Fragment() {

    var activityListener: View.OnClickListener? = null
    var itemSeleccionado: AlumnoData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v= inflater.inflate(R.layout.fragment_lista_alumnos, container, false)

        val recyclerViewLista: RecyclerView = v.findViewById<View>(R.id.recyclerviewListaAlumnos) as RecyclerView

        var items = ArrayList<AlumnoData>()

        // test data
        for (i in 1..20){
            items.add(AlumnoData(i.toString(), i.toString()))
        }

        val adapter = AlumnoDataAdapter(items) { item ->
            itemSeleccionado = item
            if (activityListener != null) {
                activityListener!!.onClick(view)
            }
        }

        recyclerViewLista.setAdapter(adapter)
        recyclerViewLista.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false))

        return v
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            ListaAlumnosFragment().apply {}
    }
}
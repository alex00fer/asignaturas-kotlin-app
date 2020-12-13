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
import com.example.tareafragmentosalejandrofernandez.database.AlumnoConAsignaturas

class ListaAlumnosFragment(var alumnos: ArrayList<AlumnoConAsignaturas>) : Fragment() {

    var activityListener: View.OnClickListener? = null
    var itemSeleccionado: AlumnoConAsignaturas? = null
    var recyclerViewLista: RecyclerView? = null
    var recyclerAdapter: AlumnoDataAdapter? = null
    //var alumnos: ArrayList<AlumnoConAsignaturas>? = null

    constructor() : this(ArrayList<AlumnoConAsignaturas>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v= inflater.inflate(R.layout.fragment_lista_alumnos, container, false)

        recyclerViewLista = v.findViewById<View>(R.id.recyclerviewListaAlumnos) as RecyclerView

        var items = alumnos!!//ArrayList<AlumnoData>()

        // test data
        //for (i in 1..20){
        //    items.add(AlumnoData(i.toString(), i.toString()))
        //}

        recyclerAdapter = AlumnoDataAdapter(items) { item ->
            itemSeleccionado = item
            if (activityListener != null) {
                activityListener!!.onClick(view)
            }
        }

        recyclerViewLista!!.setAdapter(recyclerAdapter)
        recyclerViewLista!!.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false))

        return v
    }

    fun updateData(items: ArrayList<AlumnoConAsignaturas>?) {
        if (items!=null) {
            alumnos = items
            recyclerAdapter!!.items = items
            recyclerAdapter!!.notifyDataSetChanged()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(alumnos: ArrayList<AlumnoConAsignaturas>) =
            ListaAlumnosFragment(alumnos).apply {}
    }
}
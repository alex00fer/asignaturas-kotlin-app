package com.example.tareafragmentosalejandrofernandez.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tareafragmentosalejandrofernandez.R
import com.example.tareafragmentosalejandrofernandez.database.AlumnoConAsignaturas
import com.example.tareafragmentosalejandrofernandez.models.AlumnoData


class AlumnoDataAdapter(var items: ArrayList<AlumnoConAsignaturas>, private val listener: (AlumnoConAsignaturas) -> Unit) : RecyclerView.Adapter<AlumnoDataAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlumnoDataAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        val viewHolder = ViewHolder(v)
        return viewHolder
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: AlumnoDataAdapter.ViewHolder, position: Int) {
        holder.bindItems(items[position])
        holder.itemView.setOnClickListener { listener(items[position]) }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return items.size
    }


    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textViewNombre = itemView.findViewById<TextView>(R.id.textViewListItemA)
        val textViewApellido = itemView.findViewById<TextView>(R.id.textViewListItemB)

        fun bindItems(alumno: AlumnoConAsignaturas) {

            textViewNombre.text = alumno.alumno.nombre
            textViewApellido.text = alumno.alumno.apellido
        }
    }
}
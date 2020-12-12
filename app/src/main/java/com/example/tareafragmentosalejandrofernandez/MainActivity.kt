package com.example.tareafragmentosalejandrofernandez

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tareafragmentosalejandrofernandez.fragments.ListaAlumnosFragment

class MainActivity : AppCompatActivity() {

    var frameTop: FrameLayout? = null
    var frameBottom: FrameLayout? = null
    var listaAlumnosFragment: ListaAlumnosFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        frameTop = findViewById(R.id.frameTop)
        frameBottom = findViewById(R.id.frameBottom)

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape



        } else {

            // In portrait
            Toast.makeText(this, "PORTRAIT", Toast.LENGTH_SHORT).show()

            listaAlumnosFragment = ListaAlumnosFragment.newInstance()
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
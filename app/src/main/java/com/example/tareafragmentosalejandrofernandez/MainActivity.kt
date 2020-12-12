package com.example.tareafragmentosalejandrofernandez

import android.content.res.Configuration
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    var frameTop: FrameLayout? = null
    var frameBottom: FrameLayout? = null

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
            


        }
    }
}
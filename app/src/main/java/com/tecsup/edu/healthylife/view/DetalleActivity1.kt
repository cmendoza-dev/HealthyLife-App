package com.tecsup.edu.healthylife.view

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.tecsup.edu.healthylife.R

class DetalleActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle1)
        supportActionBar?.hide()

        val buttonAtras: Button = findViewById(R.id.atras)
        buttonAtras.setOnClickListener {
            finish() // Cierra la actividad actual y vuelve a la actividad anterior
        }


    }

}
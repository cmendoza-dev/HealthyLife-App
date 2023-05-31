package com.tecsup.edu.healthylife

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView


class RecetasMedicasActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recetasmedicas)

        supportActionBar?.hide()

        val btnReceta = findViewById<Button>(R.id.btnShowMore)
        val cardView = findViewById<CardView>(R.id.cardView)

        btnReceta.setOnClickListener {
            if (cardView.visibility == View.VISIBLE) {
                cardView.visibility = View.GONE
            } else {
                cardView.visibility = View.VISIBLE
            }
        }

    }
}
package com.tecsup.edu.healthylife.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tecsup.edu.healthylife.R

class DetalleActivityDefault : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)
        supportActionBar?.hide()
    }
}
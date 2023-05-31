package com.tecsup.edu.healthylife

import android.os.Bundle
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity: AppCompatActivity() {

    lateinit var opciones: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.hide()
    }



}
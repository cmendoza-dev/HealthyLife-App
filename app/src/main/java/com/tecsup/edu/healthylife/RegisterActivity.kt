package com.tecsup.edu.healthylife

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tecsup.edu.healthylife.login.LoginActivity

class RegisterActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.hide()

        val linkTextView = findViewById<TextView>(R.id.linkTextView)
        linkTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        // Dejar vacío para no realizar ninguna acción al presionar el botón de retroceso
    }



}
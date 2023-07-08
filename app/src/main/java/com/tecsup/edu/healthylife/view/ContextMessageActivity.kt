package com.tecsup.edu.healthylife.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.tecsup.edu.healthylife.R

class ContextMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_textmessage)

        supportActionBar?.hide()

        val buttonLogin = findViewById<Button>(R.id.btnNext)
        buttonLogin.setOnClickListener {
            startActivity(Intent(this, NewPasswordActivity::class.java))
        }

    }

}
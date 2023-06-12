package com.tecsup.edu.healthylife

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.tecsup.edu.healthylife.home.HomeActivity


class NewPasswordActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newpassword)

        supportActionBar?.hide()

        val buttonLogin = findViewById<Button>(R.id.btnNextLogin)
        buttonLogin.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }


    }
}


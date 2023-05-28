package com.tecsup.edu.healthylife.authentication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.tecsup.edu.healthylife.R
import com.tecsup.edu.healthylife.home.HomeActivity

class AuthActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({

            startActivity(
                Intent(this,
                    HomeActivity::class.java
                )
            )
            finish()

        }, 2000)
    }

}
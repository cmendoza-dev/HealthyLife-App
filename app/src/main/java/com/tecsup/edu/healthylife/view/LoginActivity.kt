package com.tecsup.edu.healthylife.view

import LoginViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.tecsup.edu.healthylife.R
import com.tecsup.edu.healthylife.ResetPasswordActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        supportActionBar?.hide()

        val linkTextView = findViewById<TextView>(R.id.register)
        linkTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val forgetPassword = findViewById<TextView>(R.id.forgetPassword)
        forgetPassword.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }


        editTextEmail = findViewById(R.id.emailEt)
        editTextPassword = findViewById(R.id.passwordEt)
        buttonLogin = findViewById(R.id.btnLogin)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            loginViewModel.login(email, password)
        }


        loginViewModel.loginResult.observe(this, Observer { loggedIn ->
            if (loggedIn) {
                // El inicio de sesión fue exitoso
                // Puedes navegar a la siguiente actividad o realizar otras acciones necesarias
                // Ejemplo:
                // El inicio de sesión fue exitoso
                val name = "Juan" // Reemplaza 'Juan' con el nombre del usuario obtenido desde tu API o respuesta
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("userName", name)
                startActivity(intent)

            } else {
                // El inicio de sesión falló
                // Puedes mostrar un mensaje de error o realizar otras acciones necesarias
                AlertDialog.Builder(this)
                    .setTitle("Error de inicio de sesión")
                    .setMessage("El inicio de sesión ha fallado. Por favor, verifica tus credenciales.")
                    .setPositiveButton("Aceptar") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()

            }
        })
    }
}
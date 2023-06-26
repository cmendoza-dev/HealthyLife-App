package com.tecsup.edu.healthylife.view

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
import com.tecsup.edu.healthylife.view_model.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        val editTextEmail: TextInputEditText = findViewById(R.id.emailEt)
        val editTextPassword: TextInputEditText = findViewById(R.id.passwordEt)
        val buttonLogin: Button = findViewById(R.id.btnLogin)



        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            loginViewModel.login(email, password)
        }

        loginViewModel.loginSuccessLiveData.observe(this, Observer { isAuthenticated ->
            if (isAuthenticated) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

                // Aquí puedes realizar las acciones correspondientes al inicio de sesión exitoso
            } else {
                val dialogBuilder = AlertDialog.Builder(this)
                val inflater = layoutInflater
                val dialogView = inflater.inflate(R.layout.dialog_error_loginuser, null)
                dialogBuilder.setView(dialogView)

                val dialog = dialogBuilder.create()
                dialog.show()

                val btnAceptar = dialogView.findViewById<Button>(R.id.btnAceptar)
                btnAceptar.setOnClickListener {
                    // Acciones al hacer clic en el botón "Aceptar"
                    dialog.dismiss()
                }
                // Aquí puedes realizar las acciones correspondientes al inicio de sesión fallido
            }
        })
    }
}

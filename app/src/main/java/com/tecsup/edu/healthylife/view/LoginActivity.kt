package com.tecsup.edu.healthylife.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.tecsup.edu.healthylife.R
import com.tecsup.edu.healthylife.view_model.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        val linkCreateAccount = findViewById<TextView>(R.id.createAccount)
        linkCreateAccount.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val linkForgetPassword = findViewById<TextView>(R.id.forgetPassword)
        linkForgetPassword.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        loginViewModel.setContext(this)

        val editTextEmail: TextInputEditText = findViewById(R.id.emailEt)
        val editTextPassword: TextInputEditText = findViewById(R.id.passwordEt)
        val buttonLogin: Button = findViewById(R.id.buttonLogin)
        progressBar = findViewById(R.id.progressBar)


        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            loginViewModel.login(email, password)
        }

        loginViewModel.loginSuccessLiveData.observe(this) { isAuthenticated ->
            if (isAuthenticated) {
                // Mostrar el ProgressBar
                progressBar.visibility = View.VISIBLE

                CoroutineScope(Dispatchers.Main).launch {
                    // Simulación de una tarea de inicio de sesión que toma 2 segundos
                    delay(2000)

                    // Ocultar el ProgressBar
                    progressBar.visibility = View.INVISIBLE

                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)

                    finish()
                }


            } else {
                displayMessageError()
            }
        }
    }

    private fun displayMessageError() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_error_loginuser, null)
        dialogBuilder.setView(dialogView)

        val dialog = dialogBuilder.create()
        dialog.show()

        val btnOk = dialogView.findViewById<Button>(R.id.btnAceptar)
        btnOk.setOnClickListener {
            dialog.dismiss()
        }
    }
}

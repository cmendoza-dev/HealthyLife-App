package com.tecsup.edu.healthylife.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.tecsup.edu.healthylife.R
import com.tecsup.edu.healthylife.RegisterActivity
import com.tecsup.edu.healthylife.ResetPasswordActivity
import com.tecsup.edu.healthylife.home.HomeActivity
import com.tecsup.tecsupapp.login.LoginViewModel


class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        val edtCorreo = findViewById<EditText>(R.id.emailEditText)
        val edtClave = findViewById<EditText>(R.id.passwordEditText)

        val linkTextView = findViewById<TextView>(R.id.linkTextView)
        linkTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val forgetPassword = findViewById<TextView>(R.id.forgetPassword)
        forgetPassword.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }


        val btnIngresar = findViewById<Button>(R.id.btnIngresar)
        btnIngresar.setOnClickListener {

            val correo = edtCorreo.text.toString()
            val clave = edtClave.text.toString()
            viewModel.login(correo, clave)
        }

        observableViewModel()

        supportActionBar?.hide()
    }

    fun observableViewModel(){
        viewModel.userLoginStringError.observe(this){
            // mostrar alerta con mensaje
            alertaMensaje(it)
        }
        viewModel.userLoginServiceResponse.observe(this){
            if (it){
                startActivity(Intent(this, HomeActivity::class.java))
            }
        }
    }

    fun alertaMensaje(mensaje: String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(mensaje)
        builder.setCancelable(false)
        builder.setPositiveButton("Aceptar") {_,_ ->}

        val alertDialog = builder.create()
        alertDialog.show()

    }



}


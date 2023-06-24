package com.tecsup.edu.healthylife.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.tecsup.edu.healthylife.R
import com.tecsup.edu.healthylife.data.User
import com.tecsup.edu.healthylife.view_model.UserViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel

    private lateinit var etNombre: TextInputEditText
    private lateinit var etApellido: TextInputEditText
    private lateinit var etDNI: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etDireccion: TextInputEditText
    private lateinit var etTelefono: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnRegistrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        etNombre = findViewById(R.id.NameEt)
        etApellido = findViewById(R.id.LastEt)
        etDNI = findViewById(R.id.DniEt)
        etEmail = findViewById(R.id.EmailEt)
        etDireccion = findViewById(R.id.AddressEt)
        etTelefono = findViewById(R.id.MobileEt)
        etPassword = findViewById(R.id.PasswordEt)
        btnRegistrar = findViewById(R.id.registerBtn)

        btnRegistrar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val apellido = etApellido.text.toString()
            val dni = etDNI.text.toString().toInt()
            val email = etEmail.text.toString()
            val direccion = etDireccion.text.toString()
            val telefono = etTelefono.text.toString().toInt()
            val password = etPassword.text.toString()
            val especialidad = ""

            val user = User(
                id_user = 3,
                nombre = nombre,
                apellido = apellido,
                dni = dni,
                email = email,
                direccion = direccion,
                telefono = telefono,
                password = password,
                especialidad = especialidad
            )

            userViewModel.registerUser(user)
        }

        userViewModel.registrationStatus.observe(this, Observer { isSuccessful ->
            if (isSuccessful) {
                // Registro exitoso, puedes realizar alguna acción aquí
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            } else {
                // Registro fallido, puedes mostrar un mensaje de error

            }
        })

    }
}
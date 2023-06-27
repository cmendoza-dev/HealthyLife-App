package com.tecsup.edu.healthylife

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tecsup.edu.healthylife.data.User
import com.tecsup.edu.healthylife.view_model.LoginViewModel

class AccountActivity : AppCompatActivity() {

    private lateinit var nombreTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var celularTextView: TextView
    private lateinit var dniTextView: TextView
    private lateinit var addressTextView: TextView
    private lateinit var passwordTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        supportActionBar?.hide()

        val loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)


        nombreTextView = findViewById(R.id.nameUser)
        emailTextView = findViewById(R.id.emailUser)
        celularTextView = findViewById(R.id.mobileUser)
        addressTextView = findViewById(R.id.addressUser)
        dniTextView = findViewById(R.id.dniUser)
        passwordTextView = findViewById(R.id.passwordUser)

        val authenticatedUser: User? = loginViewModel.getAuthenticatedUser()



        authenticatedUser?.let {
            nombreTextView.text = "${it.nombre} ${it.apellido}"
            emailTextView.text = it.email
            celularTextView.text = it.telefono.toString()
            addressTextView.text = it.direccion
            dniTextView.text = it.dni.toString()
            passwordTextView.text = it.password
        }


        val buttonAtras: Button = findViewById(R.id.atras)
        buttonAtras.setOnClickListener {
            finish() // Cierra la actividad actual y vuelve a la actividad anterior
        }


    }


}
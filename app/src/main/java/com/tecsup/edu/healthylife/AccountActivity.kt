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

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        supportActionBar?.hide()

        nombreTextView = findViewById(R.id.nameUser)
        emailTextView = findViewById(R.id.emailUser)
        celularTextView = findViewById(R.id.mobileUser)
        addressTextView = findViewById(R.id.addressUser)
        dniTextView = findViewById(R.id.dniUser)
        passwordTextView = findViewById(R.id.passwordUser)

        val buttonAtras: Button = findViewById(R.id.atras)
        buttonAtras.setOnClickListener {
            finish()
        }

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        loginViewModel.setContext(this)

        val user: User? = loginViewModel.getAuthenticatedUser()
        loadUserData(user)


    }

    override fun onResume() {
        super.onResume()
        val user: User? = loginViewModel.getAuthenticatedUser()
        loadUserData(user)
    }

    private fun loadUserData(user: User?) {
        if (user != null) {
            nombreTextView.text = "${user.nombre} ${user.apellido}"
            emailTextView.text = user.email
            celularTextView.text = user.telefono.toString()
            dniTextView.text = user.dni.toString()
            addressTextView.text = user.direccion
            passwordTextView.text = user.password
        }
    }
}

package com.tecsup.edu.healthylife.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.tecsup.edu.healthylife.R
import com.tecsup.edu.healthylife.adapter.DoctorAdapter
import com.tecsup.edu.healthylife.data.User
import com.tecsup.edu.healthylife.utils.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class DetalleActivity2 : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DoctorAdapter
    private lateinit var snapHelper: SnapHelper
    private lateinit var searchEditText: EditText

    private var allUsers: List<User> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle2)
        supportActionBar?.hide()

        val buttonAtras: Button = findViewById(R.id.atras)
        buttonAtras.setOnClickListener {
            finish() // Close the current activity
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3, RecyclerView.HORIZONTAL, false)

        // Configuring the LinearSnapHelper
        snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        // Initialize the adapter with an empty list
        adapter = DoctorAdapter(emptyList())
        recyclerView.adapter = adapter


        searchEditText = findViewById(R.id.searchEditText)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterUsers(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        // Hacer la solicitud a la API y obtener los usuarios
        val apiClient = ApiClient()
        apiClient.getUsers(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    if (users != null) {
                        allUsers = users.filter { it.id_user == 1 }

                        // Actualizar el adaptador con la lista completa de usuarios
                        updateAdapter(allUsers)
                    }
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                // Handle request error
            }
        })


    }

    private fun filterUsers(searchText: String) {
        val filteredUsers = if (searchText.isNotEmpty()) {
            allUsers.filter { user ->
                user.nombre.lowercase(Locale.getDefault())
                    .contains(searchText.lowercase(Locale.getDefault())) ||
                        user.apellido.lowercase(Locale.getDefault())
                            .contains(searchText.lowercase(Locale.getDefault())) ||
                        user.especialidad.lowercase(Locale.getDefault())
                            .contains(searchText.lowercase(Locale.getDefault()))
            }
        } else {
            allUsers
        }

        updateAdapter(filteredUsers)
    }

    private fun updateAdapter(users: List<User>) {
        adapter = DoctorAdapter(users)
        recyclerView.adapter = adapter
    }
}

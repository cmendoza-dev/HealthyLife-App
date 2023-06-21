package com.tecsup.edu.healthylife.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.tecsup.edu.healthylife.AccountActivity
import com.tecsup.edu.healthylife.DatingHistoryActivity
import com.tecsup.edu.healthylife.GenerateDateActivity
import com.tecsup.edu.healthylife.R
import com.tecsup.edu.healthylife.RecetasMedicasActivity
import com.tecsup.edu.healthylife.adapter.DoctorAdapter
import com.tecsup.edu.healthylife.data.Doctor
import com.tecsup.edu.healthylife.databinding.ActivityHomeBinding
import com.tecsup.edu.healthylife.view_model.DoctorViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DoctorAdapter
    private lateinit var viewModel: DoctorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = DoctorAdapter()
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(DoctorViewModel::class.java)
        viewModel.doctors.observe(this, Observer { doctors ->
            adapter.setDoctors(doctors)
        })

        fetchData()


        val btnRightNav = findViewById<Button>(R.id.moreInfo)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        btnRightNav.setOnClickListener {
            navView.isEnabled = true
            drawerLayout.openDrawer(GravityCompat.START)
        }

        configurationNavegatioDrawer()
    }


    private fun fetchData() {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://reqres.in/api/users?page=1")
            .build()

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()
                val responseData = response.body()?.string()

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && !responseData.isNullOrEmpty()) {
                        val jsonObject = JSONObject(responseData)
                        val jsonArray = jsonObject.getJSONArray("data")

                        val doctors = ArrayList<Doctor>()

                        for (i in 0 until jsonArray.length()) {
                            val userObject = jsonArray.getJSONObject(i)
                            val firstName = userObject.getString("first_name")
                            val email = userObject.getString("email")
                            val avatar = userObject.getString("avatar")

                            val doctor = Doctor(firstName, email, avatar)
                            doctors.add(doctor)
                        }

                        viewModel.setDoctors(doctors)
                    } else {
                        // Manejar el error de la solicitud
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                // Manejar el error de la solicitud
            }
        }
    }

    private fun configurationNavegatioDrawer() {
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        navView.setNavigationItemSelectedListener { menuItem ->
            drawerLayout.closeDrawer(GravityCompat.START)
            when (menuItem.itemId) {
                R.id.date -> {
                    Toast.makeText(this, "Reservar cita", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, GenerateDateActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.recetas -> {
                    Toast.makeText(this, "Ver Recetas", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, RecetasMedicasActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.record -> {
                    Toast.makeText(this, "Historial de citas", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, DatingHistoryActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.account -> {
                    Toast.makeText(this, "Cuenta", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, AccountActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.contact_us -> {
                    Toast.makeText(this, "Contactanos", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, RegisterActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.complaints_book -> {
                    Toast.makeText(this, "Libro de reclamaciones", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, GenerateDateActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.leave -> {
                    // Acciones para cerrar sesión
                    clearSharedPreferences()
                    //invalidateAuthenticationToken()
                    //resetAppState()

                    // Redirigir al usuario a la pantalla de inicio de sesión
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)

                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    override fun onBackPressed() {
        // Dejar vacío para no realizar ninguna acción al presionar el botón de retroceso
    }

    fun clearSharedPreferences() {
        val sharedPreferences =
            getSharedPreferences("nombre_de_tu_preferencia", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

}
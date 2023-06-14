package com.tecsup.edu.healthylife.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.core.Cache
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.tecsup.edu.healthylife.*
import com.tecsup.edu.healthylife.login.LoginActivity

class HomeActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide()

        val btnRightNav = findViewById<Button>(R.id.moreInfo)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        btnRightNav.setOnClickListener {
            navView.isEnabled = true
            drawerLayout.openDrawer(GravityCompat.START)
        }

        configurationNavegatioDrawer()

    }

    private fun configurationNavegatioDrawer(){
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        navView.setNavigationItemSelectedListener { menuItem ->
            drawerLayout.closeDrawer(GravityCompat.START)
            when(menuItem.itemId){
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
        val sharedPreferences = getSharedPreferences("nombre_de_tu_preferencia", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }




}
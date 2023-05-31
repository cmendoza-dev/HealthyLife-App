package com.tecsup.edu.healthylife.home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.tecsup.edu.healthylife.*

class HomeActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide()

        val btnRightNav = findViewById<Button>(R.id.moreInfo)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val btnGenerateDate = findViewById<Button>(R.id.btnGenerateDate)
        val btnShowDate = findViewById<Button>(R.id.btnShowDate)


        btnGenerateDate.setOnClickListener {
                startActivity(Intent(this, GenerateDateActivity::class.java))
        }

        btnShowDate.setOnClickListener {
            startActivity(Intent(this, RecetasMedicasActivity::class.java))
        }

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
                R.id.record -> {
                    Toast.makeText(this, "Historial de Citas", Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(this, "Libro de Reclamaciones", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, GenerateDateActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }


}
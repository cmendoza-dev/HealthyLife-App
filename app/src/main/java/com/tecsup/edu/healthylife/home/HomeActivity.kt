package com.tecsup.edu.healthylife.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.tecsup.edu.healthylife.R
import com.tecsup.edu.healthylife.authentication.AuthActivity

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
            drawerLayout.openDrawer(GravityCompat.END)
        }

        configurationNavegatioDrawer()


    }

    private fun configurationNavegatioDrawer(){
        val navViewLeft = findViewById<NavigationView>(R.id.nav_view)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        navViewLeft.setNavigationItemSelectedListener { menuItem ->
            drawerLayout.closeDrawer(GravityCompat.START)
            when(menuItem.itemId){
                R.id.record -> {
                    Toast.makeText(this, "Historial de Citas", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.account -> {
                    Toast.makeText(this, "Cuenta", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.contact_us -> {
                    Toast.makeText(this, "Contactanos", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.complaints_book -> {
                    Toast.makeText(this, "Libro de Reclamaciones", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

}
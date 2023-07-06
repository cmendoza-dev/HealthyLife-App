package com.tecsup.edu.healthylife.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.navigation.NavigationView
import com.tecsup.edu.healthylife.DatingHistoryActivity
import com.tecsup.edu.healthylife.R
import com.tecsup.edu.healthylife.RecetasMedicasActivity
import com.tecsup.edu.healthylife.adapter.UserAdapter
import com.tecsup.edu.healthylife.data.User
import com.tecsup.edu.healthylife.utils.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserAdapter
    private lateinit var snapHelper: SnapHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()

        val btnRightNav = findViewById<Button>(R.id.moreInfo)
        val navView = findViewById<NavigationView>(R.id.nav_view)


        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get references to cards
        val card1 = findViewById<LinearLayout>(R.id.card1)
        val card2 = findViewById<LinearLayout>(R.id.card2)
        val card3 = findViewById<LinearLayout>(R.id.card3)
        val card4 = findViewById<LinearLayout>(R.id.card4)
        val card5 = findViewById<LinearLayout>(R.id.card5)
        val card6 = findViewById<LinearLayout>(R.id.card6)

        // Add click listeners to cards
        card1.setOnClickListener { changeCardColor(it) }
        card2.setOnClickListener { changeCardColor(it) }
        card3.setOnClickListener { changeCardColor(it) }
        card4.setOnClickListener { changeCardColor(it) }
        card5.setOnClickListener { changeCardColor(it) }
        card6.setOnClickListener { changeCardColor(it) }



        btnRightNav.setOnClickListener {
            navView.isEnabled = true
            drawerLayout.openDrawer(GravityCompat.START)
        }

        configureNavigationDrawer()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Configuring the LinearSnapHelper
        snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)


        // Initialize the adapter with an empty list
        adapter = UserAdapter(emptyList())
        recyclerView.adapter = adapter

        // Make the request to the API and get the users
        val apiClient = ApiClient()
        apiClient.getUsers(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    if (users != null) {
                        // Filter users with id_user equal to 1
                        val filteredUsers = users.filter { it.id_user == 1 }

                        // Update the adapter with the list of filtered users
                        adapter = UserAdapter(filteredUsers)
                        recyclerView.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                // Handle request error
            }
        })


    }

    private var selectedCard: LinearLayout? =
        null // Variable to store the currently selected card

    private fun changeCardColor(view: View) {
        val card = view as LinearLayout
        val textColorSelected = Color.WHITE // Text color when selected
        val textColorNormal = Color.BLACK // Text color in normal state


        val textView =
            card.getChildAt(1) as TextView // Get the TextView (suponiendo que está en la segunda posición)

        if (selectedCard != null && selectedCard != card) {
            // Deselect previously selected card
            selectedCard!!.setBackgroundColor(Color.WHITE) // Background color white
            (selectedCard!!.getChildAt(1) as TextView).setTextColor(textColorNormal)
            selectedCard!!.tag = null
        }

        if (card.tag == null || card.tag.toString() != "selected") {
            // The card was not selected
            card.setBackgroundColor(Color.parseColor("#6b5ad3")) // Selected background color
            textView.setTextColor(textColorSelected)
            card.tag = "selected"
            selectedCard = card

            val cardId =
                determineCardId(card) // Method to determine the cardId of the selected card
            startDetailActivity(cardId) // Show the corresponding detail activity

        } else {
            // Card was selected, restore normal colors
            card.setBackgroundColor(Color.WHITE) // Background color white
            textView.setTextColor(textColorNormal)
            card.tag = null
            selectedCard = null
        }
    }

    private fun startDetailActivity(cardId: Int) {
        val intent = Intent(this, getDetailActivityClass(cardId))
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun determineCardId(card: LinearLayout): Int {
        val cardIdString =
            card.resources.getResourceEntryName(card.id) // Get the card ID as String

        return parseCardId(cardIdString)
    }

    private fun parseCardId(cardIdString: String): Int {
        // Aquí debes implementar la lógica para convertir el String del ID a un valor numérico
        // Puedes extraer el número del String, eliminar cualquier prefijo o sufijo específico, etc.
        // Por ejemplo, si todos los ID de las tarjetas tienen el prefijo "card" seguido de un número, puedes hacer lo siguiente:
        val prefix = "card"
        val cardNumberString = cardIdString.removePrefix(prefix)
        val cardId = cardNumberString.toIntOrNull()

        return cardId
            ?: 0 // Valor predeterminado en caso de que no se pueda obtener un ID numérico válido
    }


    private fun getDetailActivityClass(cardId: Int): Class<*> {
        return when (cardId) {
            1 -> DetalleActivity1::class.java
            2 -> DetalleActivity2::class.java
            3 -> DetalleActivity3::class.java
            4 -> DetalleActivity4::class.java
            5 -> DetalleActivity5::class.java
            6 -> DetalleActivity6::class.java

            // Agrega más casos para cada tarjeta y su respectiva actividad de detalle
            //else -> DetalleActivityDefault::class.java // Actividad de detalle predeterminada en caso de no haber coincidencia
            else -> {
                DetalleActivityDefault::class.java
            }
        }
    }


    private fun configureNavigationDrawer() {
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        navView.setNavigationItemSelectedListener { menuItem ->
            drawerLayout.closeDrawer(GravityCompat.START)
            when (menuItem.itemId) {
                R.id.date -> {
                    Toast.makeText(this, "Reservar cita", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MakeDateFromUserActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.recetas -> {
                    Toast.makeText(this, "Ver recetas", Toast.LENGTH_SHORT).show()
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
                    // Actions to log out
                    clearSharedPreferences()
                    //invalidateAuthenticationToken()
                    //resetAppState()

                    // Redirect user to login screen
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

    private fun clearSharedPreferences() {
        val sharedPreferences =
            getSharedPreferences("nombre_de_tu_preferencia", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}




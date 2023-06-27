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
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.navigation.NavigationView
import com.tecsup.edu.healthylife.AccountActivity
import com.tecsup.edu.healthylife.DatingHistoryActivity
import com.tecsup.edu.healthylife.GenerateDateActivity
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

        // Obtener referencias a las tarjetas
        val card1 = findViewById<LinearLayout>(R.id.card1)
        val card2 = findViewById<LinearLayout>(R.id.card2)
        val card3 = findViewById<LinearLayout>(R.id.card3)
        val card4 = findViewById<LinearLayout>(R.id.card4)
        val card5 = findViewById<LinearLayout>(R.id.card5)
        val card6 = findViewById<LinearLayout>(R.id.card6)

        // Agregar clic listeners a las tarjetas
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

        // Configurar el LinearSnapHelper
        snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)


        // Inicializar el adaptador con una lista vacía
        adapter = UserAdapter(emptyList())
        recyclerView.adapter = adapter

        // Hacer la solicitud a la API y obtener los usuarios
        val apiClient = ApiClient()
        apiClient.getUsers(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    if (users != null) {
                        // Filtrar los usuarios con id_user igual a 1
                        val filteredUsers = users.filter { it.id_user == 1 }

                        // Actualizar el adaptador con la lista de usuarios filtrados
                        adapter = UserAdapter(filteredUsers)
                        recyclerView.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                // Manejar el error de la solicitud
            }
        })


    }

    var selectedCard: LinearLayout? =
        null // Variable para almacenar la tarjeta seleccionada actualmente

    fun changeCardColor(view: View) {
        val card = view as LinearLayout
        val textColorSelected = Color.WHITE // Color del texto cuando está seleccionada
        val textColorNormal = Color.BLACK // Color del texto en estado normal

        val imageView = card.getChildAt(0) as AppCompatImageView // Obtener el AppCompatImageView
        val textView =
            card.getChildAt(1) as TextView // Obtener el TextView (suponiendo que está en la segunda posición)

        if (selectedCard != null && selectedCard != card) {
            // Deseleccionar la tarjeta anteriormente seleccionada
            selectedCard!!.setBackgroundColor(Color.WHITE) // Color de fondo normal (transparente)
            (selectedCard!!.getChildAt(1) as TextView).setTextColor(textColorNormal)
            selectedCard!!.tag = null
        }

        if (card.tag == null || card.tag.toString() != "selected") {
            // La tarjeta no estaba seleccionada
            card.setBackgroundColor(Color.parseColor("#6b5ad3")) // Color de fondo seleccionado
            textView.setTextColor(textColorSelected)
            card.tag = "selected"
            selectedCard = card

            val cardId =
                determineCardId(card) // Método para determinar el cardId de la tarjeta seleccionada
            startDetailActivity(cardId) // Mostrar la actividad de detalle correspondiente

        } else {
            // La tarjeta estaba seleccionada, restaurar colores normales
            card.setBackgroundColor(Color.WHITE) // Color de fondo normal (transparente)
            textView.setTextColor(textColorNormal)
            card.tag = null
            selectedCard = null
        }
    }

    fun startDetailActivity(cardId: Int) {
        val intent = Intent(this, getDetailActivityClass(cardId))
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun determineCardId(card: LinearLayout): Int {
        val cardIdString =
            card.resources.getResourceEntryName(card.id) // Obtener el ID de la tarjeta como String (ejemplo: "card1")
        val cardId =
            parseCardId(cardIdString) // Método para convertir el String del ID a un valor numérico

        return cardId
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


    fun getDetailActivityClass(cardId: Int): Class<*> {
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

    private fun clearSharedPreferences() {
        val sharedPreferences =
            getSharedPreferences("nombre_de_tu_preferencia", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}




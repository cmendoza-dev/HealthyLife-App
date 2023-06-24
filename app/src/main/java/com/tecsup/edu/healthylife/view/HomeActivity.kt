package com.tecsup.edu.healthylife.view

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import com.tecsup.edu.healthylife.AccountActivity
import com.tecsup.edu.healthylife.DatingHistoryActivity
import com.tecsup.edu.healthylife.GenerateDateActivity
import com.tecsup.edu.healthylife.R
import com.tecsup.edu.healthylife.RecetasMedicasActivity
import com.tecsup.edu.healthylife.adapter.SliderAdapter
import com.tecsup.edu.healthylife.data.User
import org.json.JSONException
import org.json.JSONObject
import java.lang.Math.abs

class HomeActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var sliderAdapter: SliderAdapter
    private val dataList: MutableList<User> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()

        val txtUserName = findViewById<TextView>(R.id.userName)
        val userName = intent.getStringExtra("userName")
        if (userName != null) {
            txtUserName.text = userName
        }

        val btnRightNav = findViewById<Button>(R.id.moreInfo)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        btnRightNav.setOnClickListener {
            navView.isEnabled = true
            drawerLayout.openDrawer(GravityCompat.START)
        }

        configureNavigationDrawer()

        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.page_margin)
        val offsetPx = resources.getDimensionPixelOffset(R.dimen.offset)

        viewPager = findViewById(R.id.viewPager)

        sliderAdapter = SliderAdapter(dataList) // Asegúrate de tener un adaptador válido

        viewPager.apply {
            adapter = sliderAdapter
            addItemDecoration(MarginItemDecoration(pageMarginPx, offsetPx))
            offscreenPageLimit = 1
            setPageTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.15f
            }
        }

        val apiUrl = "http://192.168.1.9:8000/api/users?id_user=1"

        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            apiUrl,
            null,
            { response ->
                try {
                    for (i in 0 until response.length()) {
                        val userObject: JSONObject = response.getJSONObject(i)

                        val idUser: Int = userObject.getInt("id_user")
                        val nombre: String = userObject.getString("nombre")
                        val apellido: String = userObject.getString("apellido")
                        val dni: Int = userObject.getInt("dni")
                        val email: String = userObject.getString("email")
                        val direccion: String = userObject.getString("direccion")
                        val telefono: Int = userObject.getInt("telefono")
                        val password: String = userObject.getString("password")
                        val especialidad: String = userObject.getString("especialidad")

                        val userData = User(
                            idUser, nombre, apellido, dni, email, direccion,
                            telefono, password, especialidad
                        )
                        dataList.add(userData)
                    }

                    sliderAdapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                error.printStackTrace()
            })

        requestQueue.add(jsonArrayRequest)
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

class MarginItemDecoration(private val pageMarginPx: Int, private val offsetPx: Int) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        outRect.left = pageMarginPx
        outRect.right = if (position == itemCount - 1) pageMarginPx else 0
        outRect.top = offsetPx
        outRect.bottom = offsetPx
    }
}

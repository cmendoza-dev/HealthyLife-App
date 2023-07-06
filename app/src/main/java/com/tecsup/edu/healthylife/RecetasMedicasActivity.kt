package com.tecsup.edu.healthylife

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tecsup.edu.healthylife.adapter.RecetaAdapter
import com.tecsup.edu.healthylife.data.Cita
import com.tecsup.edu.healthylife.data.Receta
import com.tecsup.edu.healthylife.data.User
import com.tecsup.edu.healthylife.data.UserReceta
import com.tecsup.edu.healthylife.utils.ConfigIP
import com.tecsup.edu.healthylife.view_model.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

class RecetasMedicasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recetaAdapter: RecetaAdapter

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recetasmedicas)

        supportActionBar?.hide()

        val buttonAtras: Button = findViewById(R.id.atras)
        buttonAtras.setOnClickListener {
            finish()
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Obtain user data, appointments and recipes
        obtenerDatos()
    }

    private val ip = ConfigIP.IP

    private fun obtenerDatos() {
        val urlUsers = "http://$ip:8000/api/users/"
        val urlCitas = "http://$ip:8000/api/citas/"
        val urlRecetas = "http://$ip:8000/api/recetas/"

        val client = OkHttpClient()

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        loginViewModel.setContext(this)

        val user: User? = loginViewModel.getAuthenticatedUser()
        loadUserData(user)

        // Get the ID of the logged in user
        val userId = userId

        // Perform HTTP requests in parallel using coroutines
        GlobalScope.launch(Dispatchers.IO) {
            val usersResponse = async { fetchData(client, urlUsers) }
            val citasResponse = async { fetchData(client, urlCitas) }
            val recetasResponse = async { fetchData(client, urlRecetas) }

            val usersJsonArray = JSONArray(usersResponse.await())
            val citasJsonArray = JSONArray(citasResponse.await())
            val recetasJsonArray = JSONArray(recetasResponse.await())

            val users = parseUsers(usersJsonArray)
            val citas = parseCitas(citasJsonArray)
            val recetas = parseRecetas(recetasJsonArray)

            // Filter citations by logged in user ID
            val filteredCitas = citas.filter { it.id_paciente == userId }

            // Filter recipes by filtered citations
            val filteredRecetas =
                recetas.filter { filteredCitas.map { cita -> cita.id_cita }.contains(it.id_cita) }

            // Assign the data to the adapter of the RecyclerView in the main thread
            withContext(Dispatchers.Main) {
                recetaAdapter = RecetaAdapter(users, filteredCitas, filteredRecetas) { receta ->
                    mostrarDetallesReceta(receta)
                }
                recyclerView.adapter = recetaAdapter
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val user: User? = loginViewModel.getAuthenticatedUser()
        loadUserData(user)
    }

    private var userId: Int = 0  // Declare the userId variable

    private fun loadUserData(user: User?) {
        if (user != null) {
            userId = user.id  // Assign the user ID value to the userId variable
        }
    }

    private fun fetchData(client: OkHttpClient, url: String): String {
        val request = Request.Builder()
            .url(url)
            .build()

        val response = client.newCall(request).execute()
        return response.body?.string().orEmpty()
    }

    private fun parseUsers(jsonArray: JSONArray): List<UserReceta> {
        val users = mutableListOf<UserReceta>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val id = jsonObject.getInt("id")
            val nombre = jsonObject.getString("nombre")
            val apellido = jsonObject.getString("apellido")
            val dni = jsonObject.getInt("dni")
            val email = jsonObject.getString("email")
            val direccion = jsonObject.getString("direccion")
            val telefono = jsonObject.getInt("telefono")
            val password = jsonObject.getString("password")
            val especialidad = jsonObject.getString("especialidad")
            val idUser = jsonObject.getInt("id_user")

            val user = UserReceta(
                id,
                nombre,
                apellido,
                dni,
                email,
                direccion,
                telefono,
                password,
                especialidad,
                idUser
            )
            users.add(user)
        }

        return users
    }

    private fun parseCitas(jsonArray: JSONArray): List<Cita> {
        val citas = mutableListOf<Cita>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val idCita = jsonObject.getInt("id_cita")
            val estado = jsonObject.getBoolean("estado")
            val fechaCitaCreada = jsonObject.getString("fecha_cita_creada")
            val fechaDeCita = jsonObject.getString("fecha_de_cita")
            val triaje = jsonObject.getBoolean("triaje")
            val idPaciente = jsonObject.getInt("id_paciente")
            val idDoctor = jsonObject.getInt("id_doctor")

            val cita =
                Cita(idCita, estado, fechaCitaCreada, fechaDeCita, triaje, idPaciente, idDoctor)
            citas.add(cita)
        }

        return citas
    }

    private fun parseRecetas(jsonArray: JSONArray): List<Receta> {
        val recetas = mutableListOf<Receta>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val id = jsonObject.getInt("id")
            val diagnostico = jsonObject.getString("diagnostico")
            val indicaciones = jsonObject.getString("indicaciones")
            val recomendacion = jsonObject.getString("recomendacion")
            val idCita = jsonObject.getInt("id_cita")

            val receta = Receta(id, diagnostico, indicaciones, recomendacion, idCita)
            recetas.add(receta)
        }

        return recetas
    }

    private fun mostrarDetallesReceta(receta: Receta) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_receta, null)

        GlobalScope.launch(Dispatchers.Main) {

            dialogView.findViewById<TextView>(R.id.txtDiagnostico).text = receta.diagnostico
            dialogView.findViewById<TextView>(R.id.txtIndicaciones).text = receta.indicaciones
            dialogView.findViewById<TextView>(R.id.txtRecomendaciones).text = receta.recomendacion

            val dialogBuilder = AlertDialog.Builder(this@RecetasMedicasActivity)
                .setView(dialogView)
                .setPositiveButton("Cerrar") { dialog, _ ->
                    dialog.dismiss()
                }

            val dialog = dialogBuilder.create()
            dialog.show()
        }
    }

}

package com.tecsup.edu.healthylife.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tecsup.edu.healthylife.data.RecipeWithDoctor
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class RecipeViewModel : ViewModel() {
    private val recipesLiveData = MutableLiveData<List<RecipeWithDoctor>>()

    fun getRecipesForUser(userId: Int) {
        val citasUrl = "http://192.168.43.109:8000/api/citas/"
        val usersUrl = "http://192.168.43.109:8000/api/users/"
        val recetasUrl = "http://192.168.43.109:8000/api/recetas/"

        val citaResponse = URL(citasUrl).readText()
        val citas = JSONArray(citaResponse)

        val userResponse = URL(usersUrl).readText()
        val users = JSONArray(userResponse)

        val recetaResponse = URL(recetasUrl).readText()
        val recetas = JSONArray(recetaResponse)

        val doctorMap = mutableMapOf<Int, String>()

        for (i in 0 until users.length()) {
            val user = users.getJSONObject(i)
            val id = user.getInt("id")
            val nombre = user.getString("nombre")
            val especialidad = user.getString("especialidad")
            doctorMap[id] = "$nombre ($especialidad)"
        }

        val recipeList = mutableListOf<RecipeWithDoctor>()

        for (i in 0 until recetas.length()) {
            val receta = recetas.getJSONObject(i)
            val id = receta.getInt("id")
            val diagnostico = receta.getString("diagnostico")
            val indicaciones = receta.getString("indicaciones")
            val recomendacion = receta.getString("recomendacion")
            val idCita = receta.getInt("id_cita")

            var cita: JSONObject? = null

            for (j in 0 until citas.length()) {
                val currentCita = citas.getJSONObject(j)
                if (currentCita.getInt("id_cita") == idCita) {
                    cita = currentCita
                    break
                }
            }

            if (cita != null) {
                val idDoctor = cita.getInt("id_doctor")
                val doctor = doctorMap[idDoctor]

                if (doctor != null) {
                    val recipeWithDoctor = RecipeWithDoctor(
                        id,
                        diagnostico,
                        indicaciones,
                        recomendacion,
                        idCita,
                        doctor,
                        ""
                    )
                    recipeList.add(recipeWithDoctor)
                }
            }
        }

        recipesLiveData.value = recipeList
    }

    fun getRecipesLiveData(): LiveData<List<RecipeWithDoctor>> {
        return recipesLiveData
    }
}

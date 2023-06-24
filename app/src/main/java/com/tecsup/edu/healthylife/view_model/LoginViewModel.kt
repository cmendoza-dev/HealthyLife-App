import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tecsup.edu.healthylife.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {
    val loginResult: MutableLiveData<Boolean> = MutableLiveData()
    val userName: MutableLiveData<String> = MutableLiveData()

    fun login(email: String, password: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val user = getUserFromApi(email)
            //val loggedIn = user?.id_user == 3 && user.password == password
            val loggedIn = user?.password == password
            withContext(Dispatchers.Main) {
                userName.value = user?.nombre
                loginResult.value = loggedIn
            }
        }
    }

    private fun getUserFromApi(email: String): User? {
        // Aquí realizas la llamada a la API para obtener el usuario correspondiente al email
        // Puedes utilizar bibliotecas como Retrofit para facilitar esta tarea
        // Por ejemplo:
        // val apiService = RetrofitClient.createService(ApiService::class.java)
        // val response = apiService.getUserByEmail(email).execute()
        // return response.body()

        // En este ejemplo, se simula la respuesta de la API directamente
        val user = User(
            id_user = 1,
            nombre = "Juan",
            apellido = "Perez Rojas",
            dni = 77425632,
            email = "jperez@gmail.com",
            direccion = "Via de Evitamiento s/n, Distrito de Víctor Larco Herrera",
            telefono = 979112312,
            password = "123456789",
            especialidad = ""
        )
        return if (user.email == email) user else null
    }
}

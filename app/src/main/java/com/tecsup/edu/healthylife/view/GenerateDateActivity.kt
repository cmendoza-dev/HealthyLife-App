package com.tecsup.edu.healthylife.view

import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tecsup.edu.healthylife.R
import com.tecsup.edu.healthylife.data.Cita
import com.tecsup.edu.healthylife.data.User
import com.tecsup.edu.healthylife.view_model.CitaViewModel
import com.tecsup.edu.healthylife.view_model.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.util.Calendar


class GenerateDateActivity : AppCompatActivity() {
    // ...

    private lateinit var citaViewModel: CitaViewModel


    private lateinit var etFechaProgramada: EditText
    private lateinit var eText: EditText
    private lateinit var picker: DatePickerDialog
    private lateinit var btnMakeAppointment: Button
    private var selectedTimeLayout: LinearLayout? = null
    private var selectedTime: String? = null
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registerdate)
        supportActionBar?.hide()

        citaViewModel = ViewModelProvider(this).get(CitaViewModel::class.java)


        // Obtener datos de la actividad
        val nombreUsuario = intent.getStringExtra("nameDoctor")
        val especialidadUsuario = intent.getStringExtra("doctorSpecialty")

        // Realizar acciones o mostrar información según tus necesidades
        // Por ejemplo, mostrar el nombre del usuario y su especialidad en un TextView
        val txtNombre = findViewById<TextView>(R.id.txtDoctorFullName)
        val txtEspecialidad = findViewById<TextView>(R.id.txtDoctorEspeciality)

        txtNombre.text = nombreUsuario
        txtEspecialidad.text = especialidadUsuario


        val buttonAtras: Button = findViewById(R.id.atras)
        buttonAtras.setOnClickListener {
            finish()
        }

        // En tu método onCreate() o en algún otro lugar apropiado
        val textViewTime1 = findViewById<TextView>(R.id.text_view_time_1)
        val textViewTime2 = findViewById<TextView>(R.id.text_view_time_2)
        val textViewTime3 = findViewById<TextView>(R.id.text_view_time_3)
        val textViewTime4 = findViewById<TextView>(R.id.text_view_time_4)
        val textViewTime5 = findViewById<TextView>(R.id.text_view_time_5)
        val textViewTime6 = findViewById<TextView>(R.id.text_view_time_6)

        // En tu método onCreate() o en algún otro lugar apropiado
        val layoutTime1 = findViewById<LinearLayout>(R.id.layout_time_1)
        val layoutTime2 = findViewById<LinearLayout>(R.id.layout_time_2)
        val layoutTime3 = findViewById<LinearLayout>(R.id.layout_time_3)
        val layoutTime4 = findViewById<LinearLayout>(R.id.layout_time_4)
        val layoutTime5 = findViewById<LinearLayout>(R.id.layout_time_5)
        val layoutTime6 = findViewById<LinearLayout>(R.id.layout_time_6)



        layoutTime1.setOnClickListener {
            toggleTimeSelection(layoutTime1, textViewTime1)
        }

        layoutTime2.setOnClickListener {
            toggleTimeSelection(layoutTime2, textViewTime2)
        }

        layoutTime3.setOnClickListener {
            toggleTimeSelection(layoutTime3, textViewTime3)
        }

        layoutTime4.setOnClickListener {
            toggleTimeSelection(layoutTime4, textViewTime4)
        }

        layoutTime5.setOnClickListener {
            toggleTimeSelection(layoutTime5, textViewTime5)
        }

        layoutTime6.setOnClickListener {
            toggleTimeSelection(layoutTime6, textViewTime6)
        }


        eText = findViewById(R.id.fechaProgramadaEt)
        eText.inputType = InputType.TYPE_NULL
        eText.setOnClickListener {
            val cldr: Calendar = Calendar.getInstance()
            val day: Int = cldr.get(Calendar.DAY_OF_MONTH)
            val month: Int = cldr.get(Calendar.MONTH)
            val year: Int = cldr.get(Calendar.YEAR)
            // date picker dialog
            picker = DatePickerDialog(
                this@GenerateDateActivity,
                { _: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    eText.setText("$dayOfMonth/${monthOfYear + 1}/$year")
                },
                year,
                month,
                day
            )
            picker.show()
        }

        etFechaProgramada = eText
        btnMakeAppointment = findViewById(R.id.btnMakeAppointment)


        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        loginViewModel.setContext(this)


        val user: User? = loginViewModel.getAuthenticatedUser()
        loadUserData(user)

        /*val fechaProgramada = etFechaProgramada.text.toString()
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
        val fechaConvertida: Date = formatoFecha.parse(fechaProgramada)*/

        btnMakeAppointment.setOnClickListener {
            //guardarHoraSeleccionada()
            registrarCita()
            Toast.makeText(this, "Cita Reservada", Toast.LENGTH_SHORT).show()

        }

    }

    override fun onResume() {
        super.onResume()
        val user: User? = loginViewModel.getAuthenticatedUser()
        loadUserData(user)
    }

    private var userId: Int = 0  // Declarar la variable userId


    private fun loadUserData(user: User?) {
        if (user != null) {
            userId = user?.id ?: 0  // Asignar el valor del ID del usuario a la variable userId
        }
    }


    private var originalIconColors: MutableMap<LinearLayout, ColorStateList?> = mutableMapOf()

    private fun toggleTimeSelection(layout: LinearLayout, textView: TextView) {
        if (selectedTimeLayout == layout) {
            // Deselect the layout
            layout.isSelected = false
            selectedTimeLayout = null
            selectedTime = null
            layout.setBackgroundResource(R.drawable.time_button_white) // Change background color to white

            // Restore the original color of the icon
            val iconImageView = layout.getTag(R.id.iconImageVie1) as? ImageView
            val originalColor = originalIconColors[layout]
            iconImageView?.imageTintList = originalColor
        } else {
            selectedTimeLayout?.let {
                // Deselect the previous layout
                it.isSelected = false
                it.setBackgroundResource(R.drawable.time_button_white) // Change background color to white
                val previousIconImageView = it.getTag(R.id.iconImageVie1) as? ImageView
                val previousOriginalColor = originalIconColors[it]
                previousIconImageView?.imageTintList = previousOriginalColor
            }

            // Select the new layout
            layout.isSelected = true
            selectedTimeLayout = layout
            selectedTime = textView.text.toString()
            layout.setBackgroundResource(R.drawable.time_button_yellow) // Change background color to yellow

            // Change the color of the icon
            val iconImageView = layout.getTag(R.id.iconImageVie1) as? ImageView
            iconImageView?.imageTintList = ColorStateList.valueOf(Color.WHITE)
        }

        // For example, you can display a message with the selected time
        selectedTime?.let {
            Toast.makeText(this, "Hora seleccionada: $it", Toast.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(this, "Selecciona una hora antes de guardar", Toast.LENGTH_SHORT).show()
        }
    }


    private fun guardarHoraSeleccionada() {
        if (selectedTime != null) {
            // Aquí puedes utilizar el valor de la hora seleccionada (selectedTime) como desees
            // Por ejemplo, puedes mostrar un mensaje con la hora seleccionada
            Toast.makeText(this, "Hora seleccionada: $selectedTime", Toast.LENGTH_SHORT).show()
        } else {
            // No se ha seleccionado ninguna hora
            Toast.makeText(this, "Selecciona una hora antes de guardar", Toast.LENGTH_SHORT).show()
        }
    }

    // ...
    private fun registrarCita() {
        val idDoctor = intent.getIntExtra("idDoctor", 0)

        Log.d("TAG", "El ID del usuario es: $userId")

        val fechaActual: ZonedDateTime = ZonedDateTime.now()
        val fechaProgramada = etFechaProgramada.text.toString()
        val horaProgramada = selectedTime

        //val fechaProgramada = "30/6/2023"
        //val horaProgramada = "12:00 PM"

        /* Crear un objeto LocalDateTime a partir de las cadenas fechaProgramada y horaProgramada
        val fechaHoraFormateada = "$fechaProgramada $horaProgramada"
        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy h:mm a")
        val fechaHoraLocal = LocalDateTime.parse(fechaHoraFormateada, formatter)

        // Obtener la zona horaria actual
        val zonaHorariaActual = ZoneId.systemDefault()

        // Crear un objeto ZonedDateTime usando el LocalDateTime y la zona horaria actual
        val fechaHoraZoned = ZonedDateTime.of(fechaHoraLocal, zonaHorariaActual)*/


        // Crea un objeto CitaMedica con los datos necesarios
        val cita = Cita(
            id_cita = 1,
            id_paciente = userId,
            id_doctor = idDoctor,
            estado = false,
            fecha_cita_creada = "$fechaActual",
            fecha_de_cita = "2023-06-30T02:50:22Z",
            triaje = false
        )

        GlobalScope.launch(Dispatchers.Main) {
            citaViewModel.registrarCita(cita)

        }
    }
}

package com.tecsup.edu.healthylife

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tecsup.edu.healthylife.data.CitaMedica
import com.tecsup.edu.healthylife.view_model.CitaMedicaViewModel
import java.time.ZonedDateTime
import java.util.Calendar

class GenerateDateActivity : AppCompatActivity() {

    private lateinit var citaMedicaViewModel: CitaMedicaViewModel

    private lateinit var etFechaProgramada: EditText
    private lateinit var eText: EditText
    private lateinit var picker: DatePickerDialog
    private lateinit var btnMakeAppointment: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registerdate)
        supportActionBar?.hide()

        citaMedicaViewModel = ViewModelProvider(this).get(CitaMedicaViewModel::class.java)


        val buttonAtras: Button = findViewById(R.id.atras)
        buttonAtras.setOnClickListener {
            finish()
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
                { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
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


        //val fechaCitaCreada = "${fechaActual} + ${horaActual}"


        btnMakeAppointment.setOnClickListener {

            registrarCita()

            /*val fechaProgramada = etFechaProgramada.text.toString()
            val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
            val fechaConvertida: Date = formatoFecha.parse(fechaProgramada)*/

            // Agregar una nueva cita
            /*val cita = Cita(
                id_cita = 2,
                id_paciente = 2,
                id_doctor = 7,
                estado = false,
                fecha_cita_creada = fechaCitaCreada,
                fecha_de_cita = fechaProgramada,
                triaje = false
            )*/

        }
    }

    private fun registrarCita() {

        val fechaActual: ZonedDateTime = ZonedDateTime.now()

        // Crea un objeto CitaMedica con los datos necesarios
        val citaMedica = CitaMedica(
            id_paciente = 2,
            id_doctor = 7,
            estado = false,
            fecha_cita_creada = "fechaActual",
            fecha_de_cita = "fechaActual",
            triaje = false
        )

        // Llama a la función registrarCitaMedica del ViewModel
        citaMedicaViewModel.registrarCitaMedica(citaMedica)
    }


}

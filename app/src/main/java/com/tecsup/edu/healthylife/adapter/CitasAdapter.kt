package com.tecsup.edu.healthylife.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tecsup.edu.healthylife.R
import com.tecsup.edu.healthylife.data.Cita
import com.tecsup.edu.healthylife.data.Doctor

class CitasAdapter(
    var citasList: List<Cita>,
    var doctoresList: List<Doctor>
) : RecyclerView.Adapter<CitasAdapter.CitaViewHolder>() {

    class CitaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreDoctorTextView: TextView = itemView.findViewById(R.id.tvCitaDoctor)
        private val especialidadTextView: TextView = itemView.findViewById(R.id.tvCitaEspecialidad)
        private val fechaCitaTextView: TextView = itemView.findViewById(R.id.tvCitaFecha)

        @SuppressLint("SetTextI18n")
        fun bind(cita: Cita, doctor: Doctor) {
            nombreDoctorTextView.text = "${doctor.nombre} ${doctor.apellido}"
            especialidadTextView.text = doctor.especialidad
            fechaCitaTextView.text = cita.fecha_de_cita
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = citasList[position]
        //val doctor = doctoresList.find { it.idUser == cita.id_doctor }
        val doctor = doctoresList.find { it.id == cita.id_doctor }
        if (doctor != null) {
            Log.d(
                "CitasAdapter",
                "Cita: ${cita.id_doctor}, Doctor: ${doctor.nombre} ${doctor.apellido}"
            )
            holder.bind(cita, doctor)
        } else {
            Log.e("CitasAdapter", "No se encontró el doctor para la cita")
        }
    }


    override fun getItemCount(): Int {
        return citasList.size
    }
}


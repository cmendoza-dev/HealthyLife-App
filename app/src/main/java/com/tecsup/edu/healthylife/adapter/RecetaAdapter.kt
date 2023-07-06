package com.tecsup.edu.healthylife.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tecsup.edu.healthylife.R
import com.tecsup.edu.healthylife.data.Cita
import com.tecsup.edu.healthylife.data.Receta
import com.tecsup.edu.healthylife.data.UserReceta

class RecetaAdapter(
    private val users: List<UserReceta>,
    private val citas: List<Cita>,
    private val recetas: List<Receta>,
    private val onVerMasClick: (Receta) -> Unit
) : RecyclerView.Adapter<RecetaAdapter.RecetaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecetaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_receta, parent, false)
        return RecetaViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecetaViewHolder, position: Int) {
        val receta = recetas[position]
        val cita = citas.find { it.id_cita == receta.id_cita }
        val paciente = users.find { it.id == cita?.id_paciente }
        val doctor = users.find { it.id == cita?.id_doctor }

        holder.bind(cita, receta, paciente, doctor)
        holder.btnVerMas.setOnClickListener {
            onVerMasClick(receta)
        }
    }

    override fun getItemCount(): Int {
        return recetas.size
    }

    inner class RecetaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtFechaReceta: TextView = itemView.findViewById(R.id.txtFechaReceta)
        private val txtNombrePaciente: TextView = itemView.findViewById(R.id.txtNombrePaciente)
        private val txtEspecialidadDoctor: TextView =
            itemView.findViewById(R.id.txtEspecialidadDoctor)
        val btnVerMas: Button = itemView.findViewById(R.id.btnVerMas)

        fun bind(cita: Cita?, receta: Receta, paciente: UserReceta?, doctor: UserReceta?) {
            txtFechaReceta.text = cita?.fecha_de_cita
            txtNombrePaciente.text = "${doctor?.nombre} ${doctor?.apellido}"
            txtEspecialidadDoctor.text = doctor?.especialidad
        }
    }
}

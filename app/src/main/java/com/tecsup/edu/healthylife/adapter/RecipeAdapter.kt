package com.tecsup.edu.healthylife.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tecsup.edu.healthylife.R
import com.tecsup.edu.healthylife.data.RecipeWithDoctor

class RecipeAdapter : ListAdapter<RecipeWithDoctor, RecipeAdapter.RecipeViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recetamedica, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
    }

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewDoctor: TextView = itemView.findViewById(R.id.doctorCitaMedica)
        //private val textViewDiagnostico: TextView = itemView.findViewById(R.id.textViewDiagnostico)
        //private val textViewIndicaciones: TextView = itemView.findViewById(R.id.textViewIndicaciones)
        //private val textViewRecomendacion: TextView = itemView.findViewById(R.id.textViewRecomendacion)

        fun bind(recipe: RecipeWithDoctor) {
            textViewDoctor.text = recipe.doctorNombre
            //textViewDiagnostico.text = "Diagnóstico: ${recipe.diagnostico}"
            //textViewIndicaciones.text = "Indicaciones: ${recipe.indicaciones}"
            //textViewRecomendacion.text = "Recomendación: ${recipe.recomendacion}"
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<RecipeWithDoctor>() {
        override fun areItemsTheSame(
            oldItem: RecipeWithDoctor,
            newItem: RecipeWithDoctor
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RecipeWithDoctor,
            newItem: RecipeWithDoctor
        ): Boolean {
            return oldItem == newItem
        }
    }
}



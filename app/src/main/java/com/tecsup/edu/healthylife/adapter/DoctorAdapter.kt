package com.tecsup.edu.healthylife.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tecsup.edu.healthylife.R
import com.tecsup.edu.healthylife.data.Doctor

class DoctorAdapter : RecyclerView.Adapter<DoctorAdapter.UserViewHolder>() {
    private val doctors: ArrayList<Doctor> = ArrayList()

    fun setDoctors(doctors: List<Doctor>) {
        this.doctors.clear()
        this.doctors.addAll(doctors)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val doctor = doctors[position]
        holder.bind(doctor)
    }

    override fun getItemCount(): Int {
        return doctors.size
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val emailTextView: TextView = itemView.findViewById(R.id.emailTextView)
        private val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)

        fun bind(doctor: Doctor) {
            nameTextView.text = doctor.firstName
            emailTextView.text = doctor.email
            Glide.with(itemView)
                .load(doctor.avatar)
                .into(avatarImageView)
        }
    }
}

package com.tecsup.edu.healthylife.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tecsup.edu.healthylife.R
import com.tecsup.edu.healthylife.data.User
import java.util.Locale

class DoctorAdapter(private val users: List<User>) :
    RecyclerView.Adapter<DoctorAdapter.UserViewHolder>() {

    private var isFiltered = false
    private var filteredUsers: List<User> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_doctors, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = if (isFiltered) filteredUsers[position] else users[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return if (isFiltered) filteredUsers.size else users.size
    }

    fun filterUsers(searchText: String) {
        filteredUsers = if (searchText.isNotEmpty()) {
            users.filter { user ->
                user.id_user == 1 &&
                        (user.nombre.toLowerCase(Locale.getDefault()).contains(searchText.toLowerCase(Locale.getDefault())) ||
                                user.apellido.toLowerCase(Locale.getDefault()).contains(searchText.toLowerCase(Locale.getDefault())) ||
                                user.especialidad.toLowerCase(Locale.getDefault()).contains(searchText.toLowerCase(Locale.getDefault())))
            }
        } else {
            users.filter { user ->
                user.id_user == 1
            }
        }
        isFiltered = searchText.isNotEmpty()
        notifyDataSetChanged()
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtName: TextView = itemView.findViewById(R.id.txtName)
        private val txtEspecialidad: TextView = itemView.findViewById(R.id.txtEspecialidad)

        fun bind(user: User) {
            val fullName = "${user.nombre} ${user.apellido}"
            val especialidad = user.especialidad
            txtName.text = fullName
            txtEspecialidad.text = especialidad
        }
    }
}


package com.tecsup.edu.healthylife.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tecsup.edu.healthylife.R
import com.tecsup.edu.healthylife.data.User

class UserAdapter(private val users: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        val userViewHolder = UserViewHolder(view)
        userViewHolder.txtName = view.findViewById(R.id.txtName)
        return userViewHolder
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var txtName: TextView

        fun bind(user: User) {
            val fullName = "${user.nombre} \n ${user.apellido}"
            txtName.text = fullName
        }
    }

}

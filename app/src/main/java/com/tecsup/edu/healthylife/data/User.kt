package com.tecsup.edu.healthylife.data


import android.os.Parcel
import android.os.Parcelable

data class User(
    val id: Int,
    val id_user: Int,
    val nombre: String,
    val apellido: String,
    val dni: Int,
    val email: String,
    val direccion: String,
    val telefono: Int,
    val password: String,
    val especialidad: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(id_user)
        parcel.writeString(nombre)
        parcel.writeString(apellido)
        parcel.writeInt(dni)
        parcel.writeString(email)
        parcel.writeString(direccion)
        parcel.writeInt(telefono)
        parcel.writeString(password)
        parcel.writeString(especialidad)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}

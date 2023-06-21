package com.tecsup.edu.healthylife.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tecsup.edu.healthylife.data.Doctor

class DoctorViewModel : ViewModel() {
    private val _users = MutableLiveData<List<Doctor>>()
    val doctors: LiveData<List<Doctor>> get() = _users

    fun setDoctors(doctors: List<Doctor>) {
        _users.value = doctors
    }
}
package com.tecsup.edu.healthylife.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.edu.healthylife.data.User
import com.tecsup.edu.healthylife.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _registrationStatus = MutableLiveData<Boolean>()
    val registrationStatus: LiveData<Boolean> = _registrationStatus

    constructor() : this(UserRepository())

    fun registerUser(user: User) {
        viewModelScope.launch {
            val response = userRepository.registerUser(user)
            _registrationStatus.value = response.isSuccessful
        }
    }
}

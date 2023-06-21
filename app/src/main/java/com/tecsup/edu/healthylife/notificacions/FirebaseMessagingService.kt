package com.tecsup.edu.healthylife.notificacions

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.v("TOKEN_USUARIO", token)
        //
    }

}
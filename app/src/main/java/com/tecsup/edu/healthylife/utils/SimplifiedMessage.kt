package com.tecsup.edu.healthylife.utils

import org.json.JSONException
import org.json.JSONObject

object SimplifiedMessage {
    fun get(stringMessage: String): HashMap<String, String> {
        val messages = HashMap<String, String>()
        val jsonObject = JSONObject(stringMessage)

        try {
            if (jsonObject.has("message")) {
                val jsonMessages = jsonObject.getJSONObject("message")
                jsonMessages.keys().forEach { messages[it] = jsonMessages.getString(it) }
            } else {
                messages["message"] = ""
            }
        } catch (e: JSONException) {
            messages["message"] = ""
        }

        return messages
    }
}
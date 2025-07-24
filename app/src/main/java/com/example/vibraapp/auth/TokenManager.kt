package com.example.vibraapp.auth

import android.content.Context

class TokenManager(context: Context) {

    private val prefs = context.getSharedPreferences("vibra_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString("token", token).apply()
    }

    fun getToken(): String? {
        return prefs.getString("token", null)
    }

    fun clearToken() {
        prefs.edit().remove("token").apply()
    }
}

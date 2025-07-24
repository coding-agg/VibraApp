package com.example.vibraapp.auth

import android.content.Context
import com.example.vibraapp.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
class AuthRepository(private val context: Context) {

    private val auth = FirebaseAuth.getInstance()

    suspend fun login(email: String, password: String): Result<FirebaseUser?> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Result.success(result.user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(email: String, password: String): Result<FirebaseUser?> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            Result.success(result.user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getIdToken(): String? {
        return auth.currentUser?.getIdToken(true)?.await()?.token
    }

    fun logout() {
        auth.signOut()
    }
    suspend fun getUserProfile(uid: String): UserProfile {
        return RetrofitInstance.api.getUserProfile(uid)
    }
}

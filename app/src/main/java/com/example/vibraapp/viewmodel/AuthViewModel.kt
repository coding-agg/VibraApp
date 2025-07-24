package com.example.vibraapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.vibraapp.ProfileResponse
import com.example.vibraapp.UserProfile
import com.example.vibraapp.auth.AuthRepository
import com.example.vibraapp.auth.RetrofitInstance
import com.example.vibraapp.auth.TokenManager
import com.example.vibraapp.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel(
    private val repository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf<String?>(null)

    var profileData = mutableStateOf<ProfileResponse?>(null)

    fun getSavedToken(): String? = tokenManager.getToken()
    fun fetchUserProfile() {
        viewModelScope.launch {
            try {
                val token = repository.getIdToken()
                if (token != null) {
                    val response = RetrofitInstance.api.getProfile("Bearer $token")
                    if (response.isSuccessful) {
                        profileData.value = response.body()
                        Log.d("Profile", "Fetched: ${response.body()}")
                    } else {
                        Log.e("Profile", "Failed: ${response.code()}")
                    }
                } else {
                    Log.e("Profile", "No token")
                }
            } catch (e: Exception) {
                Log.e("Profile", "Error: ${e.message}")
            }
        }
    }

    fun login(email: String, password: String, navController: NavHostController) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            val result = repository.login(email, password)
            if (result.isSuccess) {
                val token = repository.getIdToken()
                token?.let {
                    tokenManager.saveToken(it)
                    testSecureApiCall()
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                } ?: run {
                    errorMessage.value = "Token fetch failed."
                }
            } else {
                errorMessage.value = result.exceptionOrNull()?.message
            }
            isLoading.value = false
        }
    }

    fun register(email: String, password: String, navController: NavHostController) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            val result = repository.register(email, password)
            if (result.isSuccess) {
                val token = repository.getIdToken()
                testSecureApiCall()
                token?.let {
                    tokenManager.saveToken(it)
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.REGISTER) { inclusive = true }
                    }
                } ?: run {
                    errorMessage.value = "Token fetch failed."
                }
            } else {
                errorMessage.value = result.exceptionOrNull()?.message
            }
            isLoading.value = false
        }
    }
    fun firebaseAuthWithGoogle(idToken: String, navController: NavHostController) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            try {
                FirebaseAuth.getInstance().signInWithCredential(credential).await()
                val token = repository.getIdToken()
                token?.let {
                    tokenManager.saveToken(it)
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                } ?: run {
                    errorMessage.value = "Failed to retrieve token."
                }
            } catch (e: Exception) {
                errorMessage.value = "Google sign-in failed: ${e.message}"
            }
            isLoading.value = false
        }
    }

    fun testSecureApiCall(onResult: (String) -> Unit = {}) {
        viewModelScope.launch {
            try {
                val token = FirebaseAuth.getInstance().currentUser?.getIdToken(false)?.await()?.token
                if (token != null) {
                    val response = RetrofitInstance.api.getProfile("Bearer $token")
                    if (response.isSuccessful) {
                        val result = response.body() ?: "No response body"
                        Log.d("API", "Success: $result")
                        onResult("Success: $result")
                    } else {
                        onResult("Failed: ${response.code()}")
                    }
                } else {
                    onResult("No Firebase token found")
                }
            } catch (e: Exception) {
                onResult("Exception: ${e.message}")
            }
        }
    }
    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile

    val currentUserUid: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid

    fun getUserProfile() {
        val uid = currentUserUid ?: return
        viewModelScope.launch {
            try {
                val response = repository.getUserProfile(uid)
                _userProfile.value = response
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error fetching profile: ${e.message}")
            }
        }
    }
    fun logout() {
        tokenManager.clearToken()
        FirebaseAuth.getInstance().signOut()
    }
}



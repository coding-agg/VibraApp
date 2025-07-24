package com.example.vibraapp.auth


import com.example.vibraapp.ProfileResponse
import com.example.vibraapp.UserProfile
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {
    @GET("profile")
    suspend fun getProfile(
        @Header("Authorization") authHeader: String
    ): Response<ProfileResponse>

    @GET("api/users/{uid}")
    suspend fun getUserProfile(@Path("uid") uid: String): UserProfile
}


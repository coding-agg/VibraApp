package com.example.vibraapp

data class ProfileResponse(
    val uid: String,
    val email: String?,
    val name: String?,
    val provider: String?
)

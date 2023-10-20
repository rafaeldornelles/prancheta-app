package com.rafael.core.datasource.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val refreshToken: String
)

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)

data class UserResponse(
    val _id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
)

data class RefreshTokenRequest(
    val refreshToken: String
)
package com.rafael.featureauth.domain.repository

import com.rafael.core.datasource.model.LoginRequest
import com.rafael.core.datasource.model.LoginResponse
import com.rafael.core.datasource.model.RegisterRequest
import com.rafael.core.datasource.model.UserResponse

interface UserRepository {
    suspend fun login(request: LoginRequest): Result<LoginResponse>
    suspend fun register(request: RegisterRequest): Result<UserResponse>
}
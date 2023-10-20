package com.rafael.core.datasource.endpoints

import com.rafael.core.datasource.model.LoginRequest
import com.rafael.core.datasource.model.LoginResponse
import com.rafael.core.datasource.model.RefreshTokenRequest
import com.rafael.core.datasource.model.RegisterRequest
import com.rafael.core.datasource.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserEndpoint {
    @POST("/user/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Result<LoginResponse>

    @POST("/user/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Result<UserResponse>

    @POST("/user/refresh")
    suspend fun refresh(@Body request: RefreshTokenRequest): Result<LoginResponse>
}


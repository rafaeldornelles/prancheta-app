package com.rafael.featureauth.data.repository

import com.rafael.core.datasource.endpoints.UserEndpoint
import com.rafael.core.datasource.model.LoginRequest
import com.rafael.core.datasource.model.LoginResponse
import com.rafael.core.datasource.model.RegisterRequest
import com.rafael.core.datasource.model.UserResponse
import com.rafael.featureauth.domain.repository.UserRepository

class UserRepositoryImpl(
    private val endpoint: UserEndpoint
) : UserRepository {
    override suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return endpoint.login(request)
    }

    override suspend fun register(request: RegisterRequest): Result<UserResponse> {
        return endpoint.register(request)
    }
}
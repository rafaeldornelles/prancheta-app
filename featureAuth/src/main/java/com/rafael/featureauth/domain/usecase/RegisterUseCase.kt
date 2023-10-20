package com.rafael.featureauth.domain.usecase

import com.rafael.core.datasource.model.RegisterRequest
import com.rafael.core.datasource.model.UserResponse
import com.rafael.featureauth.domain.repository.UserRepository

class RegisterUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Result<UserResponse> {
        return repository.register(RegisterRequest(firstName, lastName, email, password))
    }
}
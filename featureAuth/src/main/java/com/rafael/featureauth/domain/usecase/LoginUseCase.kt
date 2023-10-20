package com.rafael.featureauth.domain.usecase

import com.rafael.core.cache.TokenCache
import com.rafael.core.datasource.model.LoginRequest
import com.rafael.core.datasource.model.LoginResponse
import com.rafael.featureauth.domain.repository.UserRepository

class LoginUseCase(
    val repository: UserRepository,
    val tokenCache: TokenCache
) {
    suspend operator fun invoke(email: String, password: String): Result<LoginResponse> {
        return repository.login(LoginRequest(email, password)).also {
            it.getOrNull()?.let { tokens ->
                tokenCache.clear()
                tokenCache.token = tokens.token
                tokenCache.refreshToken = tokens.refreshToken
            }
        }
    }
}
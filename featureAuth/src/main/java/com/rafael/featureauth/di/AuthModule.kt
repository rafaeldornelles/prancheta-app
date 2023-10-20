package com.rafael.featureauth.di

import com.rafael.featureauth.data.repository.UserRepositoryImpl
import com.rafael.featureauth.domain.repository.UserRepository
import com.rafael.featureauth.domain.usecase.LoginUseCase
import com.rafael.featureauth.domain.usecase.RegisterUseCase
import com.rafael.featureauth.presentation.viewmodel.CreateAccountViewModel
import com.rafael.featureauth.presentation.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var authModule = module {
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { CreateAccountViewModel(get(), get()) }
    factory<UserRepository> { UserRepositoryImpl(get()) }
    factory { LoginUseCase(get(), get()) }
    factory { RegisterUseCase(get()) }
}
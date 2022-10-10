package com.rafael.featureauth.di

import com.rafael.featureauth.presentation.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var authModule = module {
    viewModel { LoginViewModel() }
}
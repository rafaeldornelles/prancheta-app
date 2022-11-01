package com.rafael.featureproject.di

import com.rafael.featureproject.data.repository.ProjectRepositoryImpl
import com.rafael.featureproject.domain.repository.ProjectRepository
import com.rafael.featureproject.domain.usecase.GetProjectsUseCase
import com.rafael.featureproject.presentation.viewmodel.ProjectViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val projectModule = module {
    viewModel { ProjectViewModel(get()) }

    single { GetProjectsUseCase(get(), get()) }

    single<ProjectRepository> { ProjectRepositoryImpl() }
}
package com.rafael.featureproject.di

import com.rafael.featureproject.data.repository.ProjectRepositoryImpl
import com.rafael.featureproject.domain.repository.ProjectRepository
import com.rafael.featureproject.domain.usecase.AddVisitationUseCase
import com.rafael.featureproject.domain.usecase.AnswerFeedbackUseCase
import com.rafael.featureproject.domain.usecase.GetProjectUseCase
import com.rafael.featureproject.domain.usecase.GetProjectsUseCase
import com.rafael.featureproject.domain.usecase.GetVisitationUseCase
import com.rafael.featureproject.domain.usecase.GetVisitationsUseCase
import com.rafael.featureproject.domain.usecase.RequestFeedbackUseCase
import com.rafael.featureproject.presentation.viewmodel.AddVisitationViewModel
import com.rafael.featureproject.presentation.viewmodel.AnswerFeedbackViewModel
import com.rafael.featureproject.presentation.viewmodel.ConstructionViewModel
import com.rafael.featureproject.presentation.viewmodel.FeedbackViewModel
import com.rafael.featureproject.presentation.viewmodel.ProjectDetailViewModel
import com.rafael.featureproject.presentation.viewmodel.ProjectViewModel
import com.rafael.featureproject.presentation.viewmodel.VisitationDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val projectModule = module {
    viewModel { ProjectViewModel(get()) }
    viewModel { (projectId: String) -> ProjectDetailViewModel(get(), projectId) }
    viewModel { (projectId: String) -> ConstructionViewModel(projectId, get()) }
    viewModel { (projectId: String) -> AddVisitationViewModel(projectId, get()) }
    viewModel { (projectId: String, visitationId: String) ->
        VisitationDetailViewModel(
            projectId,
            visitationId,
            get()
        )
    }
    viewModel { (projectId: String) -> FeedbackViewModel(projectId, get(), get()) }
    viewModel { (projectId: String) -> AnswerFeedbackViewModel(projectId, get(), get()) }

    single { GetProjectsUseCase(get()) }
    single { GetProjectUseCase(get()) }
    single { GetVisitationsUseCase(get()) }
    single { AddVisitationUseCase(get()) }
    single { GetVisitationUseCase(get()) }
    single { RequestFeedbackUseCase(get()) }
    single { AnswerFeedbackUseCase(get()) }

    single<ProjectRepository> { ProjectRepositoryImpl(get()) }
}
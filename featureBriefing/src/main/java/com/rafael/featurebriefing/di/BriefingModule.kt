package com.rafael.featurebriefing.di

import com.rafael.featurebriefing.data.repository.BriefingRepositoryImpl
import com.rafael.featurebriefing.data.repository.ProjectRepositoryImpl
import com.rafael.featurebriefing.domain.repository.BriefingRepository
import com.rafael.featurebriefing.domain.repository.ProjectRepository
import com.rafael.featurebriefing.domain.usecase.AnswerBriefingUseCase
import com.rafael.featurebriefing.domain.usecase.FindBriefingById
import com.rafael.featurebriefing.domain.usecase.GetBriefingsUseCase
import com.rafael.featurebriefing.domain.usecase.GetDefaultBriefingsUseCase
import com.rafael.featurebriefing.domain.usecase.GetQuestionsUseCase
import com.rafael.featurebriefing.domain.usecase.SendBriefingUseCase
import com.rafael.featurebriefing.domain.usecase.StartProjectUseCase
import com.rafael.featurebriefing.presentation.viewmodel.AnswerBriefingViewModel
import com.rafael.featurebriefing.presentation.viewmodel.BriefingResultViewModel
import com.rafael.featurebriefing.presentation.viewmodel.BriefingViewModel
import com.rafael.featurebriefing.presentation.viewmodel.FillBriefingClientInfoViewModel
import com.rafael.featurebriefing.presentation.viewmodel.SelectDefaultBriefingViewModel
import com.rafael.featurebriefing.presentation.viewmodel.SendBriefingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val briefingModule = module {
    single<BriefingRepository> { BriefingRepositoryImpl(get()) }
    single<ProjectRepository> { ProjectRepositoryImpl(get()) }

    single { GetQuestionsUseCase(get()) }
    single { SendBriefingUseCase(get()) }
    single { FindBriefingById(get()) }
    single { AnswerBriefingUseCase(get()) }
    single { GetBriefingsUseCase(get()) }
    single { StartProjectUseCase(get()) }
    single { GetDefaultBriefingsUseCase(get()) }

    viewModel { (defaultBriefingId: String, clientName: String, clientEmail: String) ->
        SendBriefingViewModel(
            get(),
            get(),
            defaultBriefingId,
            clientName,
            clientEmail
        )
    }
    viewModel { (formId: String) -> AnswerBriefingViewModel(formId, get(), get()) }
    viewModel { BriefingViewModel(get(), get()) }
    viewModel { (formId: String) -> BriefingResultViewModel(formId, get()) }
    viewModel { SelectDefaultBriefingViewModel(get()) }
    viewModel { FillBriefingClientInfoViewModel() }
}
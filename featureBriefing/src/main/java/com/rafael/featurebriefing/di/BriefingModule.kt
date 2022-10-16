package com.rafael.featurebriefing.di

import com.rafael.featurebriefing.data.repository.BriefingRepositoryImpl
import com.rafael.featurebriefing.domain.repository.BriefingRepository
import com.rafael.featurebriefing.domain.usecase.GetFormUseCase
import com.rafael.featurebriefing.domain.usecase.GetQuestionsUseCase
import com.rafael.featurebriefing.domain.usecase.SendBriefingUseCase
import com.rafael.featurebriefing.presentation.viewmodel.AnswerBriefingViewModel
import com.rafael.featurebriefing.presentation.viewmodel.SendBriefingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val briefingModule = module {
    single<BriefingRepository> { BriefingRepositoryImpl() }

    single { GetQuestionsUseCase(get()) }
    single { SendBriefingUseCase(get()) }
    single { GetFormUseCase(get()) }

    viewModel { SendBriefingViewModel(get(), get()) }
    viewModel { (formId: String) -> AnswerBriefingViewModel(formId, get()) }
}
package com.rafael.featurebriefing.di

import com.rafael.featurebriefing.data.repository.BriefingRepositoryImpl
import com.rafael.featurebriefing.data.repository.ProjectRepositoryImpl
import com.rafael.featurebriefing.domain.repository.BriefingRepository
import com.rafael.featurebriefing.domain.repository.ProjectRepository
import com.rafael.featurebriefing.domain.usecase.AnswerBriefingUseCase
import com.rafael.featurebriefing.domain.usecase.GetBriefingsUseCase
import com.rafael.featurebriefing.domain.usecase.GetFormUseCase
import com.rafael.featurebriefing.domain.usecase.GetQuestionsUseCase
import com.rafael.featurebriefing.domain.usecase.SendBriefingUseCase
import com.rafael.featurebriefing.domain.usecase.StartProjectUseCase
import com.rafael.featurebriefing.presentation.viewmodel.AnswerBriefingViewModel
import com.rafael.featurebriefing.presentation.viewmodel.BriefingResultViewModel
import com.rafael.featurebriefing.presentation.viewmodel.BriefingViewData
import com.rafael.featurebriefing.presentation.viewmodel.BriefingViewModel
import com.rafael.featurebriefing.presentation.viewmodel.SendBriefingViewModel
import kotlin.math.sin
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val briefingModule = module {
    single<BriefingRepository> { BriefingRepositoryImpl() }
    single<ProjectRepository> { ProjectRepositoryImpl() }

    single { GetQuestionsUseCase(get()) }
    single { SendBriefingUseCase(get(), get()) }
    single { GetFormUseCase(get()) }
    single { AnswerBriefingUseCase(get()) }
    single { GetBriefingsUseCase(get(), get()) }
    single { StartProjectUseCase(get(), get()) }

    viewModel { SendBriefingViewModel(get(), get()) }
    viewModel { (formId: String) -> AnswerBriefingViewModel(formId, get(), get()) }
    viewModel { BriefingViewModel(get(), get()) }
    viewModel { (formId: String) -> BriefingResultViewModel(formId, get()) }
}
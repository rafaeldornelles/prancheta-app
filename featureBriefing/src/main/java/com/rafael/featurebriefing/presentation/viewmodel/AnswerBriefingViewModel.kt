package com.rafael.featurebriefing.presentation.viewmodel

import com.rafael.baseui.common.BaseViewModel
import com.rafael.featurebriefing.domain.entity.BriefingForm
import com.rafael.featurebriefing.domain.usecase.GetFormUseCase

class AnswerBriefingViewModel(
    private val formId: String,
    private val getForm: GetFormUseCase
) : BaseViewModel<AnswerBriefingViewData>() {
    override suspend fun getInitial(): AnswerBriefingViewData {
        val form = getForm(formId).getOrThrow()
        return AnswerBriefingViewData(form)
    }
}

data class AnswerBriefingViewData(
    val form: BriefingForm
)
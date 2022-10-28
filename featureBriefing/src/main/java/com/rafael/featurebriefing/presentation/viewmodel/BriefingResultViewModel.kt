package com.rafael.featurebriefing.presentation.viewmodel

import com.rafael.baseui.common.BaseViewModel
import com.rafael.featurebriefing.domain.entity.BriefingForm
import com.rafael.featurebriefing.domain.usecase.GetFormUseCase

class BriefingResultViewModel(
    private val formId: String,
    private val getForm: GetFormUseCase
) : BaseViewModel<BriefingResultViewData>() {
    override suspend fun getInitial() = BriefingResultViewData(
        form = getForm(formId).getOrThrow()
    )
}

data class BriefingResultViewData(
    val form: BriefingForm
)
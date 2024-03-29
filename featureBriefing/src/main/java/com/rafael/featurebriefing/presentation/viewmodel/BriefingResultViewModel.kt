package com.rafael.featurebriefing.presentation.viewmodel

import com.rafael.baseui.common.BaseViewModel
import com.rafael.core.model.BriefingForm
import com.rafael.featurebriefing.domain.usecase.FindBriefingById

class BriefingResultViewModel(
    private val formId: String,
    private val getForm: FindBriefingById
) : BaseViewModel<BriefingResultViewData>() {
    override suspend fun getInitial() = BriefingResultViewData(
        form = getForm(formId).getOrThrow()
    )
}

data class BriefingResultViewData(
    val form: BriefingForm
)
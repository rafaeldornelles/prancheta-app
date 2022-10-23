package com.rafael.featurebriefing.presentation.viewmodel

import com.rafael.baseui.common.BaseViewModel
import com.rafael.featurebriefing.domain.entity.BriefingForm
import com.rafael.featurebriefing.domain.usecase.GetBriefingsUseCase

class BriefingViewModel(
    private val getBriefings: GetBriefingsUseCase
) : BaseViewModel<BriefingViewData>() {
    override suspend fun getInitial() = BriefingViewData(
        briefings = getBriefings().getOrThrow()
    )
}

data class BriefingViewData(
    val briefings: List<BriefingForm>
)
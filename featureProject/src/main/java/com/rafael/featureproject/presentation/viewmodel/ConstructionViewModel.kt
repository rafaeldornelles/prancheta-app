package com.rafael.featureproject.presentation.viewmodel

import com.rafael.baseui.common.BaseViewModel
import com.rafael.core.model.ConstructionVisitation
import com.rafael.featureproject.domain.usecase.GetVisitationsUseCase

class ConstructionViewModel(
    private val projectId: String,
    private val getVisitations: GetVisitationsUseCase
) : BaseViewModel<ConstructionViewData>() {
    override suspend fun getInitial() = ConstructionViewData(
        visitations = getVisitations(projectId).getOrThrow()
    )
}

data class ConstructionViewData(
    val visitations: List<ConstructionVisitation>
)
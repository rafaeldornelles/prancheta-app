package com.rafael.featureproject.presentation.viewmodel

import com.rafael.baseui.common.BaseViewModel
import com.rafael.core.model.ConstructionVisitation
import com.rafael.featureproject.domain.usecase.GetVisitationUseCase
import com.rafael.featureproject.domain.usecase.GetVisitationsUseCase

class VisitationDetailViewModel(
    val projectId: String,
    val visitationId: String,
    val getVisitation: GetVisitationUseCase
) : BaseViewModel<VisitationDetailViewData>() {
    override suspend fun getInitial() = VisitationDetailViewData(
        visitation = getVisitation(projectId, visitationId).getOrThrow()
    )
}

data class VisitationDetailViewData(
    val visitation: ConstructionVisitation
)
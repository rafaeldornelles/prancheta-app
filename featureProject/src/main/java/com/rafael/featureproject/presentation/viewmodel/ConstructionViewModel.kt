package com.rafael.featureproject.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.rafael.baseui.common.BaseViewModel
import com.rafael.core.model.ConstructionVisitation
import com.rafael.featureproject.domain.usecase.GetVisitationsUseCase
import kotlinx.coroutines.launch

class ConstructionViewModel(
    private val projectId: String,
    private val getVisitations: GetVisitationsUseCase
) : BaseViewModel<ConstructionViewData>() {
    override suspend fun getInitial() = ConstructionViewData(
        visitations = getVisitations(projectId).getOrThrow()
    )

    fun refresh() {
        viewModelScope.launch {
            getVisitations(projectId).onSuccess { visitations ->
                updateSuccess { it.copy(visitations = visitations) }
            }
        }
    }
}

data class ConstructionViewData(
    val visitations: List<ConstructionVisitation>
)
package com.rafael.featurebriefing.presentation.viewmodel

import com.rafael.baseui.common.BaseViewModel
import com.rafael.baseui.components.ButtonState
import com.rafael.core.model.DefaultBriefing
import com.rafael.featurebriefing.domain.usecase.GetDefaultBriefingsUseCase

class SelectDefaultBriefingViewModel(
    val useCase: GetDefaultBriefingsUseCase
) : BaseViewModel<SelectDefaultBriefingUiState>() {
    override suspend fun getInitial() = SelectDefaultBriefingUiState(
        defaultBriefings = useCase().getOrThrow(),
        currentSelection = null,
        buttonState = ButtonState(
            enabled = false
        )
    )

    fun onSelected(id: String) {
        updateSuccess {
            it.copy(currentSelection = id, buttonState = it.buttonState.copy(enabled = true))
        }
    }
}

data class SelectDefaultBriefingUiState(
    val defaultBriefings: List<DefaultBriefing>,
    val currentSelection: String?,
    val buttonState: ButtonState
)
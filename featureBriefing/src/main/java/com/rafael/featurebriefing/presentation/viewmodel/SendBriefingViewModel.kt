package com.rafael.featurebriefing.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.rafael.baseui.common.BaseViewModel
import com.rafael.baseui.components.ButtonState
import com.rafael.core.common.SingleShotEventBus
import com.rafael.core.model.BriefingQuestionSelection
import com.rafael.core.model.toBriefingQuestionsSelection
import com.rafael.featurebriefing.domain.usecase.GetQuestionsUseCase
import com.rafael.featurebriefing.domain.usecase.SendBriefingUseCase
import kotlinx.coroutines.launch

class SendBriefingViewModel(
    private val getQuestions: GetQuestionsUseCase,
    private val sendBriefing: SendBriefingUseCase,
    private val defaultBriefingId: String,
    private val clientName: String,
    private val clientEmail: String
) : BaseViewModel<SendBriefingUiState>() {

    private val _action = SingleShotEventBus<SendBriefingAction>()
    val action get() = _action.events

    override suspend fun getInitial(): SendBriefingUiState {
        return SendBriefingUiState(
            questions = getQuestions(defaultBriefingId).getOrThrow().toBriefingQuestionsSelection()
        )
    }

    fun onSelectionChange(id: String, isSelected: Boolean) {
        updateSuccess {
            it.copy(questions = it.questions.toMutableList().apply {
                val index = this.indexOfFirst { it.question.id == id }
                this[index] = this[index].copy(isSelected = isSelected)
            })
        }
    }

    fun sendBriefing() {
        updateSuccess { it.copy(buttonState = it.buttonState.copy(loading = true)) }
        viewModelScope.launch {
            sendBriefing(
                clientName,
                clientEmail,
                uiState.getOrNull()?.questions?.filter { it.isSelected }?.map { it.question }
                    .orEmpty()
            ).onSuccess {
                _action.postEvent(SendBriefingAction.BriefingSent)
            }.onFailure {
                _action.postEvent(SendBriefingAction.Error(it))
            }
            updateSuccess { it.copy(buttonState = it.buttonState.copy(loading = false)) }
        }
    }
}

data class SendBriefingUiState(
    val questions: List<BriefingQuestionSelection>,
    val buttonState: ButtonState = ButtonState()
)

sealed class SendBriefingAction{
    object BriefingSent: SendBriefingAction()
    data class Error(val t: Throwable) : SendBriefingAction()
}
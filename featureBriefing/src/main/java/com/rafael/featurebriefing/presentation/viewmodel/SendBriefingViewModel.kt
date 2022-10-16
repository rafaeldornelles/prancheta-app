package com.rafael.featurebriefing.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.rafael.baseui.components.TextFieldState
import com.rafael.baseui.common.BaseViewModel
import com.rafael.baseui.components.ButtonState
import com.rafael.featurebriefing.R
import com.rafael.featurebriefing.domain.entity.BriefingQuestion
import com.rafael.featurebriefing.domain.entity.BriefingQuestionSelection
import com.rafael.featurebriefing.domain.entity.toBriefingQuestionsSelection
import com.rafael.featurebriefing.domain.usecase.GetQuestionsUseCase
import com.rafael.featurebriefing.domain.usecase.SendBriefingUseCase
import kotlinx.coroutines.launch

class SendBriefingViewModel(
    private val getQuestions: GetQuestionsUseCase,
    private val sendBriefing: SendBriefingUseCase
) : BaseViewModel<SendBriefingUiState>() {
    override suspend fun getInitial(): SendBriefingUiState {
        return SendBriefingUiState(
            clientNameState = TextFieldState(
                value = "",
                label = R.string.send_briefing_client_name_label,
                placeholder = R.string.send_briefing_client_name_placeholder
            ),
            clientEmailState = TextFieldState(
                value = "",
                label = R.string.send_briefing_client_email_label,
                placeholder = R.string.send_briefing_client_email_placeholder
            ),
            questions = getQuestions().getOrThrow().toBriefingQuestionsSelection()
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
                uiState.getOrNull()?.clientNameState?.value.orEmpty(),
                uiState.getOrNull()?.clientEmailState?.value.orEmpty(),
                uiState.getOrNull()?.questions?.map { it.question }.orEmpty()
            )
            updateSuccess { it.copy(buttonState = it.buttonState.copy(loading = false)) }
        }
    }
}

data class SendBriefingUiState(
    val clientNameState: TextFieldState,
    val clientEmailState: TextFieldState,
    val questions: List<BriefingQuestionSelection>,
    val buttonState: ButtonState = ButtonState()
)
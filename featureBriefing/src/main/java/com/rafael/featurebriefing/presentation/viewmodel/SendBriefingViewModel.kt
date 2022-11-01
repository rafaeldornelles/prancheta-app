package com.rafael.featurebriefing.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.rafael.baseui.common.BaseViewModel
import com.rafael.baseui.components.ButtonState
import com.rafael.baseui.components.TextFieldState
import com.rafael.baseui.components.isValidAndNotEmpty
import com.rafael.core.common.SingleShotEventBus
import com.rafael.core.extensions.isValidEmail
import com.rafael.featurebriefing.R
import com.rafael.core.model.BriefingQuestionSelection
import com.rafael.core.model.toBriefingQuestionsSelection
import com.rafael.featurebriefing.domain.usecase.GetQuestionsUseCase
import com.rafael.featurebriefing.domain.usecase.SendBriefingUseCase
import kotlinx.coroutines.launch

class SendBriefingViewModel(
    private val getQuestions: GetQuestionsUseCase,
    private val sendBriefing: SendBriefingUseCase
) : BaseViewModel<SendBriefingUiState>() {

    private val _action = SingleShotEventBus<SendBriefingAction>()
    val action get() = _action.events

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
                placeholder = R.string.send_briefing_client_email_placeholder,
                validator = {
                    if (it.isValidEmail()) null else com.rafael.baseui.R.string.default_invalid_email_message
                }
            ),
            questions = getQuestions().getOrThrow().toBriefingQuestionsSelection()
        )
    }

    fun onClientInfoChange(name: String? = null, email: String? = null) {
        updateSuccess { state ->
            val emailNewState = state.clientEmailState.withValue(
                email ?: state.clientEmailState.value
            )
            val nameNewState = state.clientNameState.withValue(
                name ?: state.clientNameState.value
            )
            state.copy(
                clientEmailState = emailNewState,
                clientNameState = nameNewState,
                buttonState = state.buttonState.copy(enabled = nameNewState.isValidAndNotEmpty() && emailNewState.isValidAndNotEmpty())
            )
        }
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
    val clientNameState: TextFieldState,
    val clientEmailState: TextFieldState,
    val questions: List<BriefingQuestionSelection>,
    val buttonState: ButtonState = ButtonState(enabled = false)
)

sealed class SendBriefingAction{
    object BriefingSent: SendBriefingAction()
    data class Error(val t: Throwable) : SendBriefingAction()
}
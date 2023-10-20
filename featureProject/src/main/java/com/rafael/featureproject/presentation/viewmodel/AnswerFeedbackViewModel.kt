package com.rafael.featureproject.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.rafael.baseui.common.BaseViewModel
import com.rafael.baseui.components.ButtonState
import com.rafael.baseui.components.TextFieldState
import com.rafael.baseui.components.isValidAndNotEmpty
import com.rafael.core.common.SingleShotEventBus
import com.rafael.core.model.Project
import com.rafael.featureproject.R
import com.rafael.featureproject.domain.usecase.AnswerFeedbackUseCase
import com.rafael.featureproject.domain.usecase.GetProjectUseCase
import kotlinx.coroutines.launch

class AnswerFeedbackViewModel(
    private val projectId: String,
    private val getProject: GetProjectUseCase,
    private val answerFeedback: AnswerFeedbackUseCase
) : BaseViewModel<AnswerFeedbackViewData>() {

    private val _events = SingleShotEventBus<AnswerFeedbackAction>()
    val events get() = _events.events

    override suspend fun getInitial() = AnswerFeedbackViewData(
        project = getProject(projectId).getOrThrow()
    )

    fun onInputStateChange(value: String) {
        updateSuccess {
            val inputState = it.inputState.withValue(value)
            it.copy(
                inputState = inputState,
                buttonState = it.buttonState.copy(enabled = inputState.isValidAndNotEmpty())
            )
        }
    }

    fun onSendFeedback() {
        uiState.getOrNull()?.let { state ->
            viewModelScope.launch {
                setButtonLoading(true)
                answerFeedback(projectId, state.inputState.value)
                    .onSuccess {
                        _events.postEvent(AnswerFeedbackAction.Success)
                    }.onFailure {
                        _events.postEvent(AnswerFeedbackAction.Error(it))
                    }
                setButtonLoading(false)
            }
        }
    }

    private fun setButtonLoading(loading: Boolean) {
        updateSuccess {
            it.copy(buttonState = it.buttonState.copy(loading = loading))
        }
    }
}

data class AnswerFeedbackViewData(
    val project: Project,
    val buttonState: ButtonState = ButtonState(enabled = false),
    val inputState: TextFieldState = TextFieldState(
        label = R.string.feedback_label,
        placeholder = R.string.feedback_label
    )
)

sealed class AnswerFeedbackAction {
    object Success : AnswerFeedbackAction()
    data class Error(val error: Throwable) : AnswerFeedbackAction()
}
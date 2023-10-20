package com.rafael.featurebriefing.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.rafael.baseui.common.BaseViewModel
import com.rafael.baseui.components.ButtonState
import com.rafael.core.common.SingleShotEventBus
import com.rafael.core.model.BriefingForm
import com.rafael.core.model.QuestionType
import com.rafael.featurebriefing.domain.usecase.AnswerBriefingUseCase
import com.rafael.featurebriefing.domain.usecase.FindBriefingById
import kotlinx.coroutines.launch

class AnswerBriefingViewModel(
    private val formId: String,
    private val getForm: FindBriefingById,
    private val answerBriefing: AnswerBriefingUseCase
) : BaseViewModel<AnswerBriefingViewData>() {

    private val _action = SingleShotEventBus<AnswerBriefingAction>()
    val action get() = _action.events

    override suspend fun getInitial(): AnswerBriefingViewData {
        val form = getForm(formId).getOrThrow()
        return AnswerBriefingViewData(form)
    }

    fun onQuestionAnswered(id: String, answer: String) {
        updateSuccess {
            val newQuestions = it.form.questions.toMutableList().apply {
                val index = indexOfFirst { it.question.id == id }
                this[index] = this[index].copy(answer = answer)
            }
            it.copy(
                form = it.form.copy(
                    questions = newQuestions
                ),
                buttonState = it.buttonState.copy(enabled = newQuestions.filterNot { it.question.questionType == QuestionType.CHECKBOX }.none { it.answer.isNullOrBlank() })
            )
        }
    }

    fun onSendForm() {
        uiState.getOrNull()?.let {
            viewModelScope.launch {
                updateSuccess { it.copy(buttonState = it.buttonState.copy(loading = true)) }
                answerBriefing(formId, it.form).onSuccess {
                    _action.postEvent(AnswerBriefingAction.Sent)
                }.onFailure {
                    _action.postEvent(AnswerBriefingAction.Error(it))
                }
                updateSuccess { it.copy(buttonState = it.buttonState.copy(loading = false)) }
            }
        }
    }
}

data class AnswerBriefingViewData(
    val form: BriefingForm,
    val buttonState: ButtonState = ButtonState(enabled = false)
)

sealed class AnswerBriefingAction {
    object Sent: AnswerBriefingAction()
    data class Error(val t: Throwable) : AnswerBriefingAction()
}
package com.rafael.featurebriefing.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.rafael.baseui.common.BaseViewModel
import com.rafael.baseui.components.ButtonState
import com.rafael.featurebriefing.domain.entity.BriefingForm
import com.rafael.featurebriefing.domain.usecase.AnswerBriefingUseCase
import com.rafael.featurebriefing.domain.usecase.GetFormUseCase
import kotlinx.coroutines.launch

class AnswerBriefingViewModel(
    private val formId: String,
    private val getForm: GetFormUseCase,
    private val answerBriefing: AnswerBriefingUseCase
) : BaseViewModel<AnswerBriefingViewData>() {
    override suspend fun getInitial(): AnswerBriefingViewData {
        val form = getForm(formId).getOrThrow()
        return AnswerBriefingViewData(form)
    }

    fun onQuestionAnswered(id: String, answer: String) {
        updateSuccess {
            it.copy(it.form.copy(
                questions = it.form.questions.toMutableList().apply {
                    val index = indexOfFirst { it.question.id == id }
                    this[index] = this[index].copy(answer = answer)
                }
            ))
        }
    }

    fun onSendForm() {
        uiState.getOrNull()?.let {
            viewModelScope.launch {
                updateSuccess { it.copy(buttonState = it.buttonState.copy(loading = true)) }
                answerBriefing(formId, it.form)
                updateSuccess { it.copy(buttonState = it.buttonState.copy(loading = false)) }
            }
        }
    }
}

data class AnswerBriefingViewData(
    val form: BriefingForm,
    val buttonState: ButtonState = ButtonState()
)
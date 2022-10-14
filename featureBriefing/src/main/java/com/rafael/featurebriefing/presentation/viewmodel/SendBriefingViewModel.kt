package com.rafael.featurebriefing.presentation.viewmodel

import com.rafael.baseui.components.TextFieldState
import com.rafael.baseui.common.BaseViewModel
import com.rafael.featurebriefing.R
import com.rafael.featurebriefing.domain.entity.BriefingQuestion
import com.rafael.featurebriefing.domain.usecase.GetQuestionsUseCase

class SendBriefingViewModel(
    private val getQuestions: GetQuestionsUseCase
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
            questions = getQuestions().getOrThrow()
        )
    }
}

data class SendBriefingUiState(
    val clientNameState: TextFieldState,
    val clientEmailState: TextFieldState,
    val questions: List<BriefingQuestion>
)
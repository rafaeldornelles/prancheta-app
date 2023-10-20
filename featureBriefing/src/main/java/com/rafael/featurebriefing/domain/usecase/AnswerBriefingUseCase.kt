package com.rafael.featurebriefing.domain.usecase

import com.rafael.core.datasource.model.AnswerBriefingRequest
import com.rafael.core.datasource.model.toBriefingForm
import com.rafael.core.extensions.mapSuccess
import com.rafael.core.model.BriefingForm
import com.rafael.featurebriefing.domain.repository.BriefingRepository

class AnswerBriefingUseCase(
    private val repository: BriefingRepository
) {
    suspend operator fun invoke(formId: String, form: BriefingForm): Result<BriefingForm> {
        return repository.answerBriefing(
            AnswerBriefingRequest(
                briefing = formId,
                answers = form.questions.map { it.answer!! })
        )
            .mapSuccess { it.toBriefingForm() }
    }
}
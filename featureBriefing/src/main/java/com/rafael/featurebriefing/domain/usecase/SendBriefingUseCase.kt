package com.rafael.featurebriefing.domain.usecase

import com.rafael.core.datasource.model.BriefingRequest
import com.rafael.core.datasource.model.ClientResponse
import com.rafael.core.datasource.model.toBriefingForm
import com.rafael.core.model.BriefingForm
import com.rafael.core.model.BriefingQuestion
import com.rafael.core.model.toQuestionRequest
import com.rafael.featurebriefing.domain.repository.BriefingRepository

class SendBriefingUseCase(
    private val repository: BriefingRepository
) {
    suspend operator fun invoke(
        clientName: String,
        clientEmail: String,
        questions: List<BriefingQuestion>
    ): Result<BriefingForm> {
        val briefing = BriefingRequest(
            client = ClientResponse(clientName, clientEmail),
            questions = questions.map { it.toQuestionRequest() },
        )
        return repository.sendBriefing(briefing).map { it.toBriefingForm() }
    }
}


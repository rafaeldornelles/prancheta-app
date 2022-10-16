package com.rafael.featurebriefing.domain.usecase

import com.rafael.featurebriefing.domain.entity.BriefingQuestion
import com.rafael.featurebriefing.domain.repository.BriefingRepository

class SendBriefingUseCase(
    private val repository: BriefingRepository
) {
    suspend operator fun invoke(clientName: String, clientEmail: String, questions: List<BriefingQuestion>): Result<Unit> {
        return try {
            Result.success(repository.sendBriefing(clientName, clientEmail, questions))
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}
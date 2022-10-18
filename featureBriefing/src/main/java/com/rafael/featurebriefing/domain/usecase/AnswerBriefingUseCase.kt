package com.rafael.featurebriefing.domain.usecase

import com.rafael.featurebriefing.domain.entity.BriefingForm
import com.rafael.featurebriefing.domain.repository.BriefingRepository

class AnswerBriefingUseCase(
    private val repository: BriefingRepository
) {
    suspend operator fun invoke(formId: String, form: BriefingForm): Result<Unit> {
        return try {
            Result.success(repository.updateBriefing(formId, form))
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}
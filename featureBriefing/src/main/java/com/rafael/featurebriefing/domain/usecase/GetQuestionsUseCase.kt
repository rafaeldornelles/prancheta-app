package com.rafael.featurebriefing.domain.usecase

import com.rafael.core.model.BriefingQuestion
import com.rafael.featurebriefing.domain.repository.BriefingRepository

class GetQuestionsUseCase(
    private val repository: BriefingRepository
) {
    suspend operator fun invoke() : Result<List<BriefingQuestion>> {
        return try {
            Result.success(repository.getDefaultQuestions().sortedBy { it.order })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
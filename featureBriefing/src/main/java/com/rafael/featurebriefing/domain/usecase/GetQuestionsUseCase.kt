package com.rafael.featurebriefing.domain.usecase

import com.rafael.featurebriefing.domain.entity.BriefingQuestion
import com.rafael.featurebriefing.domain.repository.BriefingRepository

class GetQuestionsUseCase(
    private val repository: BriefingRepository
) {
    suspend operator fun invoke() : Result<List<BriefingQuestion>> {
        return try {
            Result.success(repository.getDefaultQuestions())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
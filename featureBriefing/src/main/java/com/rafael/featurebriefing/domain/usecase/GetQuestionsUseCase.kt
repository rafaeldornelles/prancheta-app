package com.rafael.featurebriefing.domain.usecase

import com.rafael.core.extensions.mapSuccess
import com.rafael.core.model.BriefingQuestion
import com.rafael.core.model.toBriefingQuestion
import com.rafael.featurebriefing.domain.repository.BriefingRepository

class GetQuestionsUseCase(
    private val repository: BriefingRepository
) {
    suspend operator fun invoke(id: String): Result<List<BriefingQuestion>> {
        return repository.getDefaultQuestions().mapSuccess {
            it.first { it.id == id }.questions.mapIndexed { i, q ->
                q.toBriefingQuestion(i)
            }
        }
    }
}
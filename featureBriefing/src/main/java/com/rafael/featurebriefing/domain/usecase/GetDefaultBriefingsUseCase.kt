package com.rafael.featurebriefing.domain.usecase

import com.rafael.core.extensions.mapSuccess
import com.rafael.core.model.DefaultBriefing
import com.rafael.core.model.toDefaultBriefing
import com.rafael.featurebriefing.domain.repository.BriefingRepository

class GetDefaultBriefingsUseCase(
    val repository: BriefingRepository
) {
    suspend operator fun invoke(): Result<List<DefaultBriefing>> {
        return repository.getDefaultQuestions().mapSuccess { it.map { it.toDefaultBriefing() } }
    }
}
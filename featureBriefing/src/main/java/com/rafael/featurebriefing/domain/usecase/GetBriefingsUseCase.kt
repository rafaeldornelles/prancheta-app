package com.rafael.featurebriefing.domain.usecase

import com.rafael.core.datasource.model.toBriefingForm
import com.rafael.core.extensions.mapSuccess
import com.rafael.core.model.BriefingForm
import com.rafael.featurebriefing.domain.repository.BriefingRepository

class GetBriefingsUseCase(
    private val briefingRepository: BriefingRepository
) {
    suspend operator fun invoke() : Result<List<BriefingForm>> {
        return briefingRepository.getBriefings().mapSuccess {
            it.map { it.toBriefingForm() }
        }
    }
}
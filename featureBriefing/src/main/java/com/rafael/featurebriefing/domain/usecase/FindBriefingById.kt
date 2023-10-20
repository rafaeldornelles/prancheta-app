package com.rafael.featurebriefing.domain.usecase

import com.rafael.core.datasource.model.toBriefingForm
import com.rafael.core.extensions.mapSuccess
import com.rafael.core.model.BriefingForm
import com.rafael.featurebriefing.domain.repository.BriefingRepository

class FindBriefingById(
    private val repository: BriefingRepository
) {
    suspend operator fun invoke(briefingId: String): Result<BriefingForm> {
        return repository.findById(briefingId).mapSuccess { it.toBriefingForm() }
    }
}
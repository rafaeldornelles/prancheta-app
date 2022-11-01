package com.rafael.featurebriefing.domain.usecase

import com.rafael.core.model.BriefingForm
import com.rafael.featurebriefing.domain.repository.BriefingRepository

class GetFormUseCase(
    private val repository: BriefingRepository
) {
    suspend operator fun invoke(formId: String) : Result<BriefingForm> {
        return try {
            val form = repository.getForm(formId) ?: throw IllegalArgumentException("No form found for given url")
            Result.success(form)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}
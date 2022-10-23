package com.rafael.featurebriefing.domain.usecase

import com.rafael.core.cache.UserCache
import com.rafael.featurebriefing.domain.entity.BriefingForm
import com.rafael.featurebriefing.domain.repository.BriefingRepository

class GetBriefingsUseCase(
    private val briefingRepository: BriefingRepository,
    private val userCache: UserCache
) {
    suspend operator fun invoke() : Result<List<BriefingForm>> {
        return try {
            Result.success(briefingRepository.getBriefings(userCache.currentUserId.orEmpty()))
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}
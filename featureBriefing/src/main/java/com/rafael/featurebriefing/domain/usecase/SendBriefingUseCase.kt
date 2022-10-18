package com.rafael.featurebriefing.domain.usecase

import com.rafael.core.cache.UserCache
import com.rafael.featurebriefing.domain.entity.BriefingQuestion
import com.rafael.featurebriefing.domain.repository.BriefingRepository

class SendBriefingUseCase(
    private val repository: BriefingRepository,
    private val userCache: UserCache
) {
    suspend operator fun invoke(clientName: String, clientEmail: String, questions: List<BriefingQuestion>): Result<Unit> {
        return try {
            Result.success(repository.sendBriefing(clientName, clientEmail, userCache.currentUserId.orEmpty(), questions))
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}
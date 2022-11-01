package com.rafael.featurebriefing.domain.usecase

import com.rafael.core.cache.UserCache
import com.rafael.core.model.BriefingForm
import com.rafael.core.model.Project
import com.rafael.featurebriefing.domain.repository.BriefingRepository
import com.rafael.featurebriefing.domain.repository.ProjectRepository
import java.util.*

class StartProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val briefingRepository: BriefingRepository,
    private val userCache: UserCache
) {
    suspend operator fun invoke(briefing: BriefingForm): Result<String> {
        return try {
            val project = Project(
                clientName = briefing.clientName,
                clientEmail = briefing.clientEmail,
                projectStart = Date().time,
                architectId = userCache.currentUserId.orEmpty(),
                briefing = briefing
            )
            val id = projectRepository.startProject(project)
            briefingRepository.updateBriefing(briefing.id.orEmpty(), briefing.copy(projectId = id))
            Result.success(id)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}
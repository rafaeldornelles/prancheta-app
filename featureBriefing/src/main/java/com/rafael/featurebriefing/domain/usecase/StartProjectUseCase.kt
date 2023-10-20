package com.rafael.featurebriefing.domain.usecase

import com.rafael.core.datasource.model.ProjectRequest
import com.rafael.core.extensions.mapSuccess
import com.rafael.core.model.BriefingForm
import com.rafael.featurebriefing.domain.repository.ProjectRepository

class StartProjectUseCase(
    private val projectRepository: ProjectRepository,
) {
    suspend operator fun invoke(briefing: BriefingForm): Result<String> {
        return projectRepository.startProject(ProjectRequest(briefing.clientName, briefing.id!!))
            .mapSuccess { it._id }
    }
}
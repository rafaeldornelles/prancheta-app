package com.rafael.featureproject.domain.usecase

import com.rafael.core.model.Project
import com.rafael.featureproject.domain.repository.ProjectRepository

class UpdateProjectUseCase(
    val projectRepository: ProjectRepository
) {
    suspend operator fun invoke(projectId: String, project: Project): Result<Unit> {
        return try {
            Result.success(projectRepository.updateProject(projectId, project))
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}
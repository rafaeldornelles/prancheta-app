package com.rafael.featureproject.domain.usecase

import com.rafael.core.model.Project
import com.rafael.featureproject.domain.repository.ProjectRepository

class GetProjectUseCase(
    private val projectRepository: ProjectRepository
) {
    suspend operator fun invoke(projectId: String) : Result<Project> {
        return try {
            Result.success(projectRepository.getProject(projectId)!!)
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}
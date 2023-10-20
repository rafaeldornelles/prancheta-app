package com.rafael.featureproject.domain.usecase

import com.rafael.core.datasource.model.toProject
import com.rafael.core.extensions.mapSuccess
import com.rafael.core.model.Project
import com.rafael.featureproject.domain.repository.ProjectRepository

class GetProjectUseCase(
    private val projectRepository: ProjectRepository
) {
    suspend operator fun invoke(projectId: String) : Result<Project> {
        return projectRepository.getProject(projectId).mapSuccess { it.toProject() }
    }
}
package com.rafael.featureproject.domain.usecase

import com.rafael.core.datasource.model.toProject
import com.rafael.core.extensions.mapSuccess
import com.rafael.core.model.Project
import com.rafael.featureproject.domain.repository.ProjectRepository

class GetProjectsUseCase(
    private val repository: ProjectRepository
) {
    suspend operator fun invoke() : Result<List<Project>> {
        return repository.getProjects().mapSuccess { it.map { it.toProject() } }
    }
}
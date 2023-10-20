package com.rafael.featureproject.data.repository

import com.rafael.core.datasource.endpoints.ProjectEndpoint
import com.rafael.core.datasource.model.ProjectResponse
import com.rafael.core.datasource.model.ProjectStepRequest
import com.rafael.core.datasource.model.ProjectStepResponse
import com.rafael.featureproject.domain.repository.ProjectRepository

class ProjectRepositoryImpl(
    private val endpoint: ProjectEndpoint
) : ProjectRepository {

    override suspend fun getProjects(): Result<List<ProjectResponse>> {
        return endpoint.getProjects()
    }

    override suspend fun getProject(projectId: String): Result<ProjectResponse> {
        return endpoint.findById(projectId)
    }

    override suspend fun addStep(step: ProjectStepRequest): Result<ProjectStepResponse> {
        return endpoint.addStep(step)
    }
}
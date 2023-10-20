package com.rafael.featurebriefing.data.repository

import com.rafael.core.datasource.endpoints.ProjectEndpoint
import com.rafael.core.datasource.model.ProjectRequest
import com.rafael.core.datasource.model.ProjectResponse
import com.rafael.featurebriefing.domain.repository.ProjectRepository

class ProjectRepositoryImpl(
    private val endpoint: ProjectEndpoint
) : ProjectRepository {
    override suspend fun startProject(project: ProjectRequest): Result<ProjectResponse> {
        return endpoint.insertProject(project)
    }
}
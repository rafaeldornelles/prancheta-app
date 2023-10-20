package com.rafael.featurebriefing.domain.repository

import com.rafael.core.datasource.model.ProjectRequest
import com.rafael.core.datasource.model.ProjectResponse

interface ProjectRepository {
    suspend fun startProject(project: ProjectRequest): Result<ProjectResponse>
}
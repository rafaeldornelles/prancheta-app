package com.rafael.featureproject.domain.repository

import com.rafael.core.datasource.model.ProjectResponse
import com.rafael.core.datasource.model.ProjectStepRequest
import com.rafael.core.datasource.model.ProjectStepResponse

interface ProjectRepository {
    suspend fun getProjects(): Result<List<ProjectResponse>>
    suspend fun getProject(projectId: String): Result<ProjectResponse>
    suspend fun addStep(step: ProjectStepRequest): Result<ProjectStepResponse>
}
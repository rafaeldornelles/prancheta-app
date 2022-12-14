package com.rafael.featureproject.domain.repository

import com.rafael.core.model.ConstructionVisitation
import com.rafael.core.model.Project

interface ProjectRepository {
    suspend fun getProjects(userId: String) : List<Project>
    suspend fun getProject(projectId: String) : Project?
    suspend fun getVisitations(projectId: String) : List<ConstructionVisitation>
    suspend fun addVisitation(projectId: String, visitation: ConstructionVisitation)
    suspend fun getVisitation(projectId: String, visitationId: String) : ConstructionVisitation?
    suspend fun updateProject(projectId: String, project: Project)
}
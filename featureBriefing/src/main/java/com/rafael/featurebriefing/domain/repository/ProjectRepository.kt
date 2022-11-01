package com.rafael.featurebriefing.domain.repository

import com.rafael.core.model.Project

interface ProjectRepository {
    suspend fun startProject(project: Project) : String
}
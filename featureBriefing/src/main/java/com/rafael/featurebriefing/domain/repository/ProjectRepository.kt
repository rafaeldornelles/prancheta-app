package com.rafael.featurebriefing.domain.repository

import com.rafael.featurebriefing.domain.entity.Project

interface ProjectRepository {
    suspend fun startProject(project: Project) : String
}
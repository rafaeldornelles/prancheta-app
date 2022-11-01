package com.rafael.featureproject.domain.repository

import com.rafael.core.model.Project

interface ProjectRepository {
    suspend fun getProjects(userId: String) : List<Project>
}
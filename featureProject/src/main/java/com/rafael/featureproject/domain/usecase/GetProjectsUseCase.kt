package com.rafael.featureproject.domain.usecase

import com.rafael.core.cache.UserCache
import com.rafael.core.model.Project
import com.rafael.featureproject.domain.repository.ProjectRepository

class GetProjectsUseCase(
    private val repository: ProjectRepository,
    private val userCache: UserCache
) {
    suspend operator fun invoke() : Result<List<Project>> {
        return try {
            Result.success(repository.getProjects(userCache.currentUserId.orEmpty()))
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}
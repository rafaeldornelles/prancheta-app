package com.rafael.featureproject.domain.usecase

import com.rafael.core.model.ConstructionVisitation
import com.rafael.featureproject.domain.repository.ProjectRepository

class GetVisitationsUseCase(
    private val repository: ProjectRepository
) {
    suspend operator fun invoke(projectId: String) : Result<List<ConstructionVisitation>> {
        return try {
            Result.success(repository.getVisitations(projectId))
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}
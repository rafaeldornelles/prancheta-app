package com.rafael.featureproject.domain.usecase

import com.rafael.core.model.ConstructionVisitation
import com.rafael.featureproject.domain.repository.ProjectRepository

class AddVisitationUseCase(
    private val repository: ProjectRepository
) {
    suspend operator fun invoke(
        projectId: String,
        visitation: ConstructionVisitation
    ) : Result<Unit> {
        return try {
            Result.success(repository.addVisitation(projectId, visitation))
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}
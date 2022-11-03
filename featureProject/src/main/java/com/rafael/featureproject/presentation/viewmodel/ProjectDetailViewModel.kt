package com.rafael.featureproject.presentation.viewmodel

import com.rafael.baseui.common.BaseViewModel
import com.rafael.core.model.Project
import com.rafael.featureproject.domain.usecase.GetProjectUseCase

class ProjectDetailViewModel(
    private val getProject: GetProjectUseCase,
    private val projectId: String
) : BaseViewModel<ProjectDetailViewData>() {
    override suspend fun getInitial() = ProjectDetailViewData(
        project = getProject(projectId).getOrThrow()
    )
}

data class ProjectDetailViewData(
    val project: Project
)
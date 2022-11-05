package com.rafael.featureproject.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.rafael.baseui.common.BaseViewModel
import com.rafael.core.model.ConstructionVisitation
import com.rafael.core.model.Project
import com.rafael.featureproject.domain.usecase.GetProjectUseCase
import com.rafael.featureproject.domain.usecase.GetVisitationsUseCase
import kotlinx.coroutines.launch

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
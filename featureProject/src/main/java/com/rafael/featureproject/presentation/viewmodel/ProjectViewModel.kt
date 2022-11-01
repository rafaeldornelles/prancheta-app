package com.rafael.featureproject.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.rafael.baseui.common.BaseViewModel
import com.rafael.core.model.Project
import com.rafael.featureproject.domain.usecase.GetProjectsUseCase
import kotlinx.coroutines.launch

class ProjectViewModel(
    private val getProjects: GetProjectsUseCase
) : BaseViewModel<ProjectViewData>() {
    override suspend fun getInitial(): ProjectViewData = ProjectViewData(
        projects = getProjects().getOrThrow()
    )

    fun refresh() {
        viewModelScope.launch {
            setLoading()
            getProjects().onSuccess { list ->
                setSuccess(ProjectViewData(list))
            }.onFailure {
                setError(it)
            }
        }
    }
}

data class ProjectViewData(
    val projects: List<Project>
)
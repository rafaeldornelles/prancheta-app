package com.rafael.featureproject.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.rafael.baseui.common.BaseViewModel
import com.rafael.core.model.Project
import com.rafael.featureproject.domain.usecase.GetProjectUseCase
import com.rafael.featureproject.domain.usecase.RequestFeedbackUseCase
import kotlinx.coroutines.launch

class FeedbackViewModel(
    private val projectId: String,
    private val getProject: GetProjectUseCase,
    private val requestFeedbackUseCase: RequestFeedbackUseCase
) : BaseViewModel<FeedBackViewData>() {
    override suspend fun getInitial() = FeedBackViewData(
        project = getProject(projectId).getOrThrow()
    )

    fun concludeProject() {
        uiState.getOrNull()?.let {
            viewModelScope.launch {
                setLoading()
                requestFeedbackUseCase(projectId).getOrElse {
                    setError(it)
                    return@launch
                }
                getProject(projectId).onSuccess {
                    setSuccess(FeedBackViewData(it))
                }.onFailure { setError(it) }
            }
        }
    }
}

data class FeedBackViewData(
    val project: Project
)
package com.rafael.featurebriefing.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.rafael.baseui.common.BaseViewModel
import com.rafael.baseui.components.ButtonState
import com.rafael.core.common.SingleShotEventBus
import com.rafael.featurebriefing.domain.entity.BriefingForm
import com.rafael.featurebriefing.domain.usecase.GetBriefingsUseCase
import com.rafael.featurebriefing.domain.usecase.StartProjectUseCase
import kotlinx.coroutines.launch

class BriefingViewModel(
    private val getBriefings: GetBriefingsUseCase,
    private val startProject: StartProjectUseCase
) : BaseViewModel<BriefingViewData>() {

    private val _action = SingleShotEventBus<BriefingAction>()
    val action get() = _action.events

    override suspend fun getInitial() = BriefingViewData(
        briefings = getBriefings().getOrThrow()
    )

    fun refresh() {
        viewModelScope.launch {
            setLoading()
            getBriefings().onSuccess { list ->
                setSuccess(BriefingViewData(list))
            }.onFailure {
                setError(it)
            }
        }
    }

    fun initProject(form: BriefingForm, index: Int) {
        viewModelScope.launch {
            setSecondaryLoading(index, true)
            startProject(form).onSuccess {
                _action.postEvent(BriefingAction.ProjectCreated(it))
            }.onFailure {
                _action.postEvent((BriefingAction.Error(it)))
            }
            setSecondaryLoading(index, false)
        }
    }

    private fun setSecondaryLoading(index: Int, loading: Boolean) {
        updateSuccess {
            it.copy(secondaryState = it.secondaryState.toMutableList().apply {
                set(index, this[index].copy(loading = loading))
            })
        }
    }
}

data class BriefingViewData(
    val briefings: List<BriefingForm>,
    val secondaryState: List<ButtonState> = List(briefings.size) { ButtonState() }
)

sealed class BriefingAction {
    data class ProjectCreated(val projectId: String) : BriefingAction()
    data class Error(val t: Throwable) : BriefingAction()
}
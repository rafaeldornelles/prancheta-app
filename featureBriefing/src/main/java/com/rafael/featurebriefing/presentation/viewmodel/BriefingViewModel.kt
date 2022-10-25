package com.rafael.featurebriefing.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.rafael.baseui.common.BaseViewModel
import com.rafael.baseui.common.UiState
import com.rafael.featurebriefing.domain.entity.BriefingForm
import com.rafael.featurebriefing.domain.usecase.GetBriefingsUseCase
import kotlinx.coroutines.launch

class BriefingViewModel(
    private val getBriefings: GetBriefingsUseCase
) : BaseViewModel<BriefingViewData>() {
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
}

data class BriefingViewData(
    val briefings: List<BriefingForm>
)
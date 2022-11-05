package com.rafael.featureproject.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.rafael.baseui.common.BaseViewModel
import com.rafael.baseui.components.TextFieldState
import com.rafael.core.common.SingleShotEventBus
import com.rafael.core.extensions.formatDate
import com.rafael.core.extensions.parsedate
import com.rafael.core.extensions.toBase64
import com.rafael.core.model.ConstructionVisitation
import com.rafael.featureproject.R
import com.rafael.featureproject.domain.usecase.AddVisitationUseCase
import java.io.File
import java.util.*
import kotlinx.coroutines.launch

class AddVisitationViewModel(
    private val projectId: String,
    private val addVisitation: AddVisitationUseCase
) : BaseViewModel<AddVisitationViewData>() {

    private val _action = SingleShotEventBus<AddVisitationAction>()
    val action get() = _action.events

    override suspend fun getInitial() = AddVisitationViewData()

    fun onDateChange(value: Long) {
        updateSuccess {
            it.copy(date = it.date.withValue(value.formatDate()))
        }
    }

    fun onObservationChange(value: String) {
        updateSuccess {
            it.copy(observation = it.observation.withValue(value))
        }
    }

    fun addFile(file: File?) {
        file?.let { newFile ->
            updateSuccess { it.copy(images = it.images.toMutableList().apply {
                add(newFile)
            }) }
        }
    }

    fun saveVisitation() {
        uiState.getOrNull()?.let { state ->
            viewModelScope.launch {
                val visitation = ConstructionVisitation(
                    id = null,
                    date = state.date.value.parsedate().time,
                    observation = state.observation.value,
                    images = state.images.map {
                        it.toBase64()
                    }
                )
                addVisitation(projectId, visitation).onSuccess {
                    _action.postEvent(AddVisitationAction.VisitationAdded)
                }.onFailure {
                    _action.postEvent(AddVisitationAction.Error(it))
                }
            }
        }
    }
}

data class AddVisitationViewData(
    val date: TextFieldState = TextFieldState(
        value = Date().time.formatDate(),
        label = R.string.date_label,
        placeholder = R.string.date_label
    ),
    val observation: TextFieldState = TextFieldState(
        label = R.string.observation_label,
        placeholder = R.string.observation_label
    ),
    val images: List<File> = emptyList()
)

sealed class AddVisitationAction {
    object VisitationAdded : AddVisitationAction()
    data class Error(val t: Throwable) : AddVisitationAction()
}
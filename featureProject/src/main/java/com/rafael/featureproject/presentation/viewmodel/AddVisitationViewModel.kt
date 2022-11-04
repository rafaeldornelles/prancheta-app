package com.rafael.featureproject.presentation.viewmodel

import com.rafael.baseui.common.BaseViewModel
import com.rafael.baseui.components.TextFieldState
import com.rafael.core.extensions.formatDate
import com.rafael.featureproject.R
import java.io.File
import java.util.*

class AddVisitationViewModel(
    private val projectId: String
) : BaseViewModel<AddVisitationViewData>() {
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
package com.rafael.core.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseViewModel<T> : ViewModel() {
    var uiState: UiState<T> by mutableStateOf(UiState.Loading)
        protected set

    init {
        viewModelScope.launch {
            uiState = UiState.Success(getInitial())
        }
    }

    abstract suspend fun getInitial(): T

    fun setLoading() {
        uiState = UiState.Loading
    }

    fun setSuccess(value: T) {
        uiState = UiState.Success(value)
    }

    fun updateSuccess(block: (oldValue: T) -> T) {
        uiState.getOrNull()?.let {
            uiState = UiState.Success(block(it))
        }
    }
}

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val error: Throwable) : UiState<Nothing>()

    fun getOrNull() : T? = (this as? Success)?.data
    fun getOrThrow() : T = (this as? Success)?.data ?: throw IllegalStateException()
    fun errorOrNull() : Throwable? = (this as? Error)?.error
    fun isLoading() : Boolean = (this as? Loading) != null
}
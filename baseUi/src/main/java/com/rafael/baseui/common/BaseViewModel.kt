package com.rafael.baseui.common

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class BaseViewModel<T> : ViewModel() {
    private val _uiStateValue by lazy {
        mutableStateOf(UiState.Loading as UiState<T>).apply {
            viewModelScope.launch {
                value = try {
                    UiState.Success(getInitial())
                } catch (e: Exception) {
                    UiState.Error(e)
                }
            }
        }
    }
    var uiState: UiState<T> get() = _uiStateValue.value
        private set(value) { _uiStateValue.value = value }

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
package com.rafael.featureauth.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.rafael.baseui.R
import com.rafael.baseui.common.BaseViewModel
import com.rafael.baseui.components.ButtonState
import com.rafael.baseui.components.TextFieldState
import com.rafael.core.cache.TokenCache
import com.rafael.core.common.Constants.PASSWORD_MIN_LENGTH
import com.rafael.core.common.SingleShotEventBus
import com.rafael.core.extensions.isValidEmail
import com.rafael.featureauth.domain.usecase.LoginUseCase
import com.rafael.featureauth.domain.usecase.RegisterUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val tokenCache: TokenCache
) : BaseViewModel<LoginUiState>() {

    val _action = SingleShotEventBus<LoginAction>()
    val action get() = _action.events

    override suspend fun getInitial() = LoginUiState(
        emailState = TextFieldState(
            label = R.string.default_email,
            placeholder = R.string.default_email_placeholder,
            validator = {
                if (it.isValidEmail()) null else R.string.default_invalid_email_message
            },
            value = "rafael@email.com" // TODO: REMOVER
        ),
        passwordState = TextFieldState(
            label = R.string.default_password,
            placeholder = R.string.default_password_placeholder,
            validator = {
                if (it.length >= PASSWORD_MIN_LENGTH) null else R.string.default_invalid_password_message
            },
            value = "password" //TODO: REMOVER
        ),
        buttonState = ButtonState(
            enabled = true, //TODO: TROCAR
            loading = false
        )
    )

    fun onEmailChange(value: String) {
        updateSuccess {
            it.copy(
                emailState = it.emailState.withValue(value),
                buttonState = it.buttonState.copy(enabled = it.isButtonEnabled(newEmail = value))
            )
        }
    }

    fun onPasswordChange(value: String) {
        updateSuccess {
            it.copy(
                passwordState = it.passwordState.withValue(value),
                buttonState = it.buttonState.copy(enabled = it.isButtonEnabled(newPassword = value))
            )
        }
    }

    private fun setButtonLoading(loading: Boolean = true) {
        updateSuccess {
            it.copy(buttonState = it.buttonState.copy(loading = loading))
        }
    }

    fun login() {
        uiState.getOrNull()?.let { state ->
            val email = state.emailState.value.trim()
            val password = state.passwordState.value.trim()
            viewModelScope.launch {
                setButtonLoading()
                val result = loginUseCase(email, password)
                if (result.isFailure) _action.postEvent(LoginAction.LoginAuthError)
                setButtonLoading(false)
            }
        }
    }
}

data class LoginUiState(
    val emailState: TextFieldState,
    val passwordState: TextFieldState,
    val buttonState: ButtonState
)

fun LoginUiState.isButtonEnabled(
    newEmail: String? = null,
    newPassword: String? = null
): Boolean {
    return emailState.validator?.invoke(newEmail ?: emailState.value.trim()) == null &&
            passwordState.validator?.invoke(newPassword ?: passwordState.value) == null
}

sealed class LoginAction {
    object LoginAuthError : LoginAction()
    object LoginGenericError : LoginAction()
}
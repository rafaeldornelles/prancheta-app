package com.rafael.featureauth.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.rafael.baseui.R
import com.rafael.baseui.common.BaseViewModel
import com.rafael.baseui.components.ButtonState
import com.rafael.baseui.components.TextFieldState
import com.rafael.core.common.Constants
import com.rafael.core.common.SingleShotEventBus
import com.rafael.core.extensions.isValidEmail
import com.rafael.featureauth.domain.usecase.LoginUseCase
import com.rafael.featureauth.domain.usecase.RegisterUseCase
import kotlinx.coroutines.launch

class CreateAccountViewModel(
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase
) : BaseViewModel<CreateAccountUiState>() {

    val _action = SingleShotEventBus<CreateAccountAction>()
    val action get() = _action.events
    override suspend fun getInitial() = CreateAccountUiState(
        firstNameState = TextFieldState(
            label = R.string.default_first_name,
            placeholder = R.string.default_first_name_placeholder,
            value = ""
        ),
        lastNameState = TextFieldState(
            label = R.string.default_last_name,
            placeholder = R.string.default_last_name_placeholder,
            value = ""
        ),
        emailState = TextFieldState(
            label = R.string.default_email,
            placeholder = R.string.default_email_placeholder,
            validator = {
                if (it.isValidEmail()) null else R.string.default_invalid_email_message
            },
            value = ""
        ),
        passwordState = TextFieldState(
            label = R.string.default_password,
            placeholder = R.string.default_password_placeholder,
            validator = {
                if (it.length >= Constants.PASSWORD_MIN_LENGTH) null else R.string.default_invalid_password_message
            },
            value = ""
        ),
        buttonState = ButtonState(
            enabled = false,
            loading = false
        )
    )

    fun onFirstNameChange(value: String) {
        updateSuccess {
            it.copy(
                firstNameState = it.firstNameState.withValue(value),
                buttonState = it.buttonState.copy(enabled = it.isButtonEnabled(newFirstName = value))
            )
        }
    }

    fun onLastNameChange(value: String) {
        updateSuccess {
            it.copy(
                lastNameState = it.lastNameState.withValue(value),
                buttonState = it.buttonState.copy(enabled = it.isButtonEnabled(newLastName = value))
            )
        }
    }

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

    fun createAccount() {
        uiState.getOrNull()?.let { state ->
            val firstName = state.firstNameState.value.trim()
            val lastName = state.lastNameState.value.trim()
            val email = state.emailState.value.trim()
            val password = state.passwordState.value.trim()
            viewModelScope.launch {
                setButtonLoading()
                val result = registerUseCase(firstName, lastName, email, password)
                if (result.isFailure) _action.postEvent(CreateAccountAction.CreateAccountError)
                loginUseCase(email, password)
                setButtonLoading(false)
            }
        }
    }
}

data class CreateAccountUiState(
    val firstNameState: TextFieldState,
    val lastNameState: TextFieldState,
    val emailState: TextFieldState,
    val passwordState: TextFieldState,
    val buttonState: ButtonState
)

fun CreateAccountUiState.isButtonEnabled(
    newEmail: String? = null,
    newPassword: String? = null,
    newFirstName: String? = null,
    newLastName: String? = null
): Boolean {
    return emailState.validator?.invoke(newEmail ?: emailState.value.trim()) == null &&
            passwordState.validator?.invoke(newPassword ?: passwordState.value) == null &&
            (newFirstName ?: firstNameState.value).isNotBlank() && (newLastName
        ?: lastNameState.value).isNotBlank()
}

sealed class CreateAccountAction {
    object CreateAccountError : CreateAccountAction()
}
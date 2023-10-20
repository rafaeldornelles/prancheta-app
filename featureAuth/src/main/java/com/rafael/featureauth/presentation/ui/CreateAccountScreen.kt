package com.rafael.featureauth.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.rafael.baseui.components.Button
import com.rafael.baseui.components.TextField
import com.rafael.baseui.scaffold.Scaffold
import com.rafael.baseui.scaffold.TopAppBar
import com.rafael.baseui.theme.spacing
import com.rafael.featureauth.R
import com.rafael.featureauth.presentation.viewmodel.CreateAccountAction
import com.rafael.featureauth.presentation.viewmodel.CreateAccountViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.getViewModel

@Composable
fun CreateAccountScreen(
    viewModel: CreateAccountViewModel = getViewModel()
) {
    val context = LocalContext.current
    val invalidCredentialsMessage =  stringResource(R.string.login_create_account_error)
    LaunchedEffect(Unit) {
        viewModel.action.collectLatest {
            if (it is CreateAccountAction.CreateAccountError) {
                Toast.makeText(context, invalidCredentialsMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
    Scaffold(
        state = viewModel.uiState,
        topBar = { TopAppBar(actions = emptyList(), title = {}, shouldShowBackButton = true) }
    ) { state ->
        Column(modifier = Modifier.verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.x700))
            Text(
                text = stringResource(R.string.create_account_label),
                style = MaterialTheme.typography.h6
            )
            Text(text = stringResource(R.string.create_account_action))
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.x500))
            TextField(state = state.firstNameState, onValueChange = viewModel::onFirstNameChange)
            TextField(state = state.lastNameState, onValueChange = viewModel::onLastNameChange)
            TextField(state = state.emailState, onValueChange = viewModel::onEmailChange)
            TextField(
                state = state.passwordState,
                onValueChange = viewModel::onPasswordChange,
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.x200))
            Button(
                modifier = Modifier.fillMaxWidth(),
                state = state.buttonState,
                text = stringResource(R.string.create_account_label),
                onClick = viewModel::createAccount,
            )
        }
    }
}
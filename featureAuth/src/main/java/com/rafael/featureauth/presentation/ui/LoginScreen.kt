package com.rafael.featureauth.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavController
import com.rafael.baseui.components.Button
import com.rafael.baseui.components.ButtonSecondary
import com.rafael.baseui.components.TextField
import com.rafael.baseui.scaffold.Scaffold
import com.rafael.baseui.theme.spacing
import com.rafael.featureauth.R
import com.rafael.featureauth.presentation.navigation.AuthRoutes
import com.rafael.featureauth.presentation.viewmodel.LoginAction
import com.rafael.featureauth.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = getViewModel()
) {
    val context = LocalContext.current
    val invalidCredentialsMessage =  stringResource(R.string.login_invalid_credentials_message)
    val genericErrorMessage =  stringResource(R.string.login_generic_eeor__message)
    LaunchedEffect(Unit) {
        viewModel.action.collectLatest {
            when(it) {
                is LoginAction.LoginAuthError -> Toast.makeText(context, invalidCredentialsMessage, Toast.LENGTH_LONG).show()
                is LoginAction.LoginGenericError -> Toast.makeText(context, genericErrorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
    Scaffold(
        state = viewModel.uiState,
        topBar = {}
    ) { state ->
        Column(modifier = Modifier.verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.x700))
            Image(
                modifier = Modifier.fillMaxWidth(0.7f),
                painter = painterResource(id = com.rafael.baseui.R.drawable.prancheta_logo),
                contentDescription = null)
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.x500))
            Text(text = stringResource(R.string.login_screen_title), style = MaterialTheme.typography.h6)
            Text(text = stringResource(R.string.login_scrin_call_to_action))
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.x500))
            TextField(state = state.emailState, onValueChange = viewModel::onEmailChange)
            TextField(state = state.passwordState, onValueChange = viewModel::onPasswordChange, visualTransformation = PasswordVisualTransformation())
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.x300))
            Button(
                modifier = Modifier.fillMaxWidth(),
                state = state.buttonState,
                text = stringResource(R.string.login_label),
                onClick = viewModel::login,
                )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.x200))
            ButtonSecondary(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.create_account_label),
                onClick = { navController.navigate(AuthRoutes.CreateAccount.route)}
            )
        }
    }
}
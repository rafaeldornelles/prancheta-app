package com.rafael.featureauth.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.rafael.baseui.components.Button
import com.rafael.baseui.components.TextField
import com.rafael.baseui.scaffold.Scaffold
import com.rafael.featureauth.R
import com.rafael.featureauth.presentation.navigation.AuthRoutes
import com.rafael.featureauth.presentation.viewmodel.LoginAction
import com.rafael.featureauth.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = getViewModel()
) {
    val context = LocalContext.current
    val invalidCredentialsMessage =  stringResource(R.string.login_invalid_credentials_message)
    LaunchedEffect(Unit) {
        viewModel.action.collectLatest {
            if (it is LoginAction.LoginError) {
                Toast.makeText(context, invalidCredentialsMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
    Scaffold(
        state = viewModel.uiState
    ) { state ->
        Column {
            Text(text = stringResource(R.string.login_screen_title))
            Text(text = stringResource(R.string.login_scrin_call_to_action))
            TextField(state = state.emailState, onValueChange = viewModel::onEmailChange)
            TextField(state = state.passwordState, onValueChange = viewModel::onPasswordChange)
            Button(
                modifier = Modifier.fillMaxWidth(),
                state = state.buttonState,
                text = stringResource(R.string.login_label),
                onClick = viewModel::login,
                )
            Button(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.create_account_label),
                onClick = { navController.navigate(AuthRoutes.CreateAccount.route)}
            )
        }
    }
}
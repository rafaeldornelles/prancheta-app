package com.rafael.featurebriefing.presentation.viewmodel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rafael.baseui.common.BaseViewModel
import com.rafael.baseui.components.Button
import com.rafael.baseui.components.ButtonState
import com.rafael.baseui.components.TextField
import com.rafael.baseui.components.TextFieldState
import com.rafael.baseui.components.isValidAndNotEmpty
import com.rafael.baseui.scaffold.Scaffold
import com.rafael.baseui.theme.spacing
import com.rafael.core.common.withArgs
import com.rafael.core.extensions.isValidEmail
import com.rafael.featurebriefing.R
import com.rafael.featurebriefing.presentation.navigation.BriefingRoutes
import com.rafael.featurebriefing.presentation.navigation.BriefingRoutes.Companion.CLIENT_EMAIL_KEY
import com.rafael.featurebriefing.presentation.navigation.BriefingRoutes.Companion.CLIENT_NAME_KEY
import org.koin.androidx.compose.getViewModel

class FillBriefingClientInfoViewModel : BaseViewModel<FillBriefingClientInfoUiState>() {
    override suspend fun getInitial() = FillBriefingClientInfoUiState(
        clientNameState = TextFieldState(
            value = "",
            label = R.string.send_briefing_client_name_label,
            placeholder = R.string.send_briefing_client_name_placeholder
        ),
        clientEmailState = TextFieldState(
            value = "",
            label = R.string.send_briefing_client_email_label,
            placeholder = R.string.send_briefing_client_email_placeholder,
            validator = {
                if (it.isValidEmail()) null else com.rafael.baseui.R.string.default_invalid_email_message
            }
        ),
        buttonState = ButtonState(
            enabled = false
        )
    )

    fun onClientInfoChange(name: String? = null, email: String? = null) {
        updateSuccess { state ->
            val emailNewState = state.clientEmailState.withValue(
                email ?: state.clientEmailState.value
            )
            val nameNewState = state.clientNameState.withValue(
                name ?: state.clientNameState.value
            )
            state.copy(
                clientEmailState = emailNewState,
                clientNameState = nameNewState,
                buttonState = state.buttonState.copy(enabled = nameNewState.isValidAndNotEmpty() && emailNewState.isValidAndNotEmpty())
            )
        }
    }
}

data class FillBriefingClientInfoUiState(
    val clientNameState: TextFieldState,
    val clientEmailState: TextFieldState,
    val buttonState: ButtonState
)

@Composable
fun FillBriefingClientInfoScreen(
    navController: NavController,
    viewModel: FillBriefingClientInfoViewModel = getViewModel()
) {
    Scaffold(state = viewModel.uiState) { state ->
        Column(Modifier.verticalScroll(rememberScrollState())) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.x300))
            Text(
                text = "Enviar Briefing",
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.x300),
                style = MaterialTheme.typography.h4
            )
            Text(text = "O briefing é a primeira etapa de um projeto de arquitetura e consiste em alinhar expectativas entre arquiteto e cliente. \n\nNesta etapa, vamos preencher os dados do cliente, selecionar as perguntas a serem respondidas e enviar um link para o cliente acessar o formulário de briefing.")
            Divider(
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.x300),
                color = MaterialTheme.colors.primary,
                thickness = 1.dp
            )
            Text(text = "1. Preencha os dados do cliente")
            TextField(
                state = state.clientNameState,
                onValueChange = { viewModel.onClientInfoChange(name = it) }
            )
            TextField(
                state = state.clientEmailState,
                onValueChange = { viewModel.onClientInfoChange(email = it) })
            Divider(
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.x300),
                color = MaterialTheme.colors.primary,
                thickness = 1.dp
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.spacing.x300),
                state = state.buttonState,
                text = "Enviar",
                onClick = {
                    navController.navigate(
                        BriefingRoutes.SelectDefaultBriefing.withArgs(
                            CLIENT_NAME_KEY to state.clientNameState.value,
                            CLIENT_EMAIL_KEY to state.clientEmailState.value
                        )
                    )
                }
            )
        }

    }
}
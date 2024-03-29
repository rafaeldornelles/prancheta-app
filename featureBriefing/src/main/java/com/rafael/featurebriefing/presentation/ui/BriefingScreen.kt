package com.rafael.featurebriefing.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.rafael.baseui.components.Button
import com.rafael.baseui.components.ButtonSecondary
import com.rafael.baseui.components.Card
import com.rafael.baseui.components.HelperAlert
import com.rafael.baseui.components.KeyValueText
import com.rafael.baseui.scaffold.Scaffold
import com.rafael.baseui.scaffold.TopAppBar
import com.rafael.baseui.scaffold.TopAppBarAction
import com.rafael.baseui.theme.spacing
import com.rafael.core.common.withArgs
import com.rafael.featurebriefing.presentation.navigation.BriefingRoutes
import com.rafael.featurebriefing.presentation.navigation.BriefingRoutes.Companion.BRIEFING_ID_KEY
import com.rafael.featurebriefing.presentation.viewmodel.BriefingAction
import com.rafael.featurebriefing.presentation.viewmodel.BriefingViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun BriefingScreen(
    navController: NavController,
    viewModel: BriefingViewModel,
    onProjectsReload: () -> Unit,
    context: Context = LocalContext.current,
    clipboardManager: ClipboardManager = LocalClipboardManager.current
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.action.collectLatest {
            when (it) {
                is BriefingAction.ProjectCreated -> {
                    viewModel.refresh()
                    onProjectsReload()
                }
                is BriefingAction.Error -> {
                    Toast.makeText(
                        context,
                        "Something went wrong. Try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    Scaffold(
        state = viewModel.uiState,
        topBar = {
            TopAppBar(
                scaffoldState = it,
                actions = listOf(
                    TopAppBarAction(
                        "Adicionar",
                        Icons.Outlined.Add
                    ) { navController.navigate(BriefingRoutes.FillBriefingClientInfo.route) }
                )
            )
        },
        horizontalPadding = 0.dp
    ) { state ->
        if (state.briefings.isEmpty()) {
            Column {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.x300))
                Text("Briefing", style = MaterialTheme.typography.h4)
                HelperAlert(
                    text = "Você ainda não adiciou nenhum briefing. Envie um briefing pra um cliente e ele deverá aparecer aqui",
                    vector = Icons.Outlined.Info
                )
            }
        } else {
            LazyColumn {
                item {
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.x300))
                    Text(text = "Briefing", style = MaterialTheme.typography.h4)
                }
                item {
                    Text(text = "Os briefings que foram enviados aos seus clientes aparecerão aqui")
                }
                items(state.briefings.size) {
                    val briefing = state.briefings[it]
                    val isAnswered = briefing.answerTime != null
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(MaterialTheme.spacing.x300)) {
                            KeyValueText(key = "Cliente", value = briefing.clientName)
                            Spacer(Modifier.height(MaterialTheme.spacing.x100))
                            KeyValueText(key = "E-mail", value = briefing.clientEmail)
                            Spacer(Modifier.height(MaterialTheme.spacing.x100))
                            KeyValueText(
                                key = "Status",
                                value = if (isAnswered) "Respondido" else "Não Respondido"
                            )
                            if (isAnswered) {
                                Row {
                                    Button(
                                        modifier = Modifier.weight(1f),
                                        text = "Ver Respostas",
                                        height = 20.dp
                                    ) {
                                        navController.navigate(
                                            BriefingRoutes.BriefingResults.withArgs(
                                                BRIEFING_ID_KEY to briefing.id.orEmpty()
                                            )
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.x200))
                                    if (briefing.projectId == null) {
                                        ButtonSecondary(
                                            state = state.secondaryState[it],
                                            modifier = Modifier.weight(1f),
                                            text = "Iniciar Projeto",
                                            height = 20.dp,
                                        ) {
                                            viewModel.initProject(briefing, it)
                                        }
                                    } else {
                                        ButtonSecondary(
                                            modifier = Modifier.weight(1f),
                                            text = "Ver Projeto",
                                            height = 20.dp
                                        ) {
                                            navController.navigate("project-detail?id=${briefing.projectId.orEmpty()}")
                                        }
                                    }
                                }
                            } else {

                                Row {
                                    val url =
                                        "https://prancheta.com/briefing/answer-briefing?id=${briefing.id}"
                                    Button(
                                        modifier = Modifier.weight(1f),
                                        text = "Copiar URL", height = 20.dp, onClick = {
                                            clipboardManager.setText(AnnotatedString(url))
                                            Toast.makeText(
                                                context,
                                                "Url copiada para a área de transferência",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        })
                                    ButtonSecondary(
                                        modifier = Modifier.weight(1f),
                                        text = "Responder", height = 20.dp

                                    ) {
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            url.toUri()
                                        ).apply { context.startActivity(this) }
                                    }
                                }
                                Log.d("aaaa", Build.VERSION.SDK_INT.toString())
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                    Text(
                                        text = "Atenção: A partir do android 12, o Google introduziu verificações de " +
                                                "segurança que exigem que para o funcionamento automático dos deeplinking, " +
                                                "o desenvolvedor publique um certificado no host de destino do link. Devido à " +
                                                "impossibilidade de fazer essa publicação na etapa atual do desenvolvimento do app, " +
                                                "para fins de teste da ferramenta, solicita-se que o usuário acesse, nas configurações, " +
                                                "as informações do app, selecione 'abrir por padrão', clique em 'adicionar link', e " +
                                                "inclua a permissão manualmente para o domínio 'prancheta.com'; Depois disso, será possível " +
                                                "acessar os links diretos para as áreas do aplicativo."
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
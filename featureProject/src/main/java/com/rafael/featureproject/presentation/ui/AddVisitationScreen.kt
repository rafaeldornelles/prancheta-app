package com.rafael.featureproject.presentation.ui

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.rafael.baseui.components.Button
import com.rafael.baseui.components.TextField
import com.rafael.baseui.scaffold.Scaffold
import com.rafael.baseui.theme.spacing
import com.rafael.core.extensions.createImageFile
import com.rafael.core.extensions.parsedate
import com.rafael.core.extensions.showDatePicker
import com.rafael.featureproject.presentation.viewmodel.AddVisitationAction
import com.rafael.featureproject.presentation.viewmodel.AddVisitationViewModel
import com.rafael.featureproject.presentation.viewmodel.ProjectViewModel
import java.io.File
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AddVisitationScreen(
    navController: NavController,
    projectId: String,
    viewModel: AddVisitationViewModel = getViewModel() {
        parametersOf(projectId)
    }
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    var currentFile: File? by remember {
        mutableStateOf(null)
    }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { isSuccess: Boolean ->
            if (isSuccess) {
                viewModel.addFile(currentFile)
            }
        }
    )
    LaunchedEffect(key1 = Unit) {
        viewModel.action.collectLatest {
            when(it) {
                AddVisitationAction.VisitationAdded -> {
                    navController.popBackStack()
                }
                is AddVisitationAction.Error -> {
                    Toast.makeText(context, it.t.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    Scaffold(state = viewModel.uiState) { state ->
        LazyColumn() {
            item {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.x300))
                Text("Adicionar visita", style = MaterialTheme.typography.h4)
                Text(text = "Adicione as informações referentes à sua visita")
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged {
                            if (it.isFocused) {
                                context.showDatePicker(updatedDate = {
                                    viewModel.onDateChange(it)
                                    focusManager.clearFocus()
                                }, date = state.date.value.parsedate().time)
                            }
                        },
                    state = state.date, onValueChange = {}, trailingIcon = {
                        Icon(imageVector = Icons.Outlined.CalendarMonth, contentDescription = null)
                    }
                )
                TextField(modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp), state = state.observation, onValueChange = viewModel::onObservationChange, lineCount = Int.MAX_VALUE)
                Text("Fotos", style = MaterialTheme.typography.h5)
                Text(text = "Adicione fotos da sua visita!")
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.x200))
                LazyRow() {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(end = MaterialTheme.spacing.x200)
                                .size(150.dp)
                                .border(1.dp, Color.LightGray, MaterialTheme.shapes.medium)
                                .clickable {
                                    currentFile = context
                                        .createImageFile()
                                        .apply {
                                            val uri = FileProvider.getUriForFile(
                                                context,
                                                "application_authority",
                                                this
                                            )
                                            cameraLauncher.launch(uri)
                                        }
                                }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Add,
                                contentDescription = null,
                                tint = Color.LightGray
                            )
                        }
                    }
                    items(state.images) {
                        Image(
                            modifier = Modifier
                                .padding(end = MaterialTheme.spacing.x200)
                                .size(150.dp)
                                .aspectRatio(1f),
                            contentScale = ContentScale.Crop,
                            painter = rememberAsyncImagePainter(model = it.absolutePath),
                            contentDescription = null
                        )
                    }
                }
                Button(modifier = Modifier
                    .padding(vertical = MaterialTheme.spacing.x400)
                    .fillMaxWidth(), text = "Salvar") {
                    viewModel.saveVisitation()
                }
            }
        }
    }
}
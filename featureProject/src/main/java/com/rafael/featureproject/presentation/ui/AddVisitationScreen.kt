package com.rafael.featureproject.presentation.ui

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
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
import com.rafael.featureproject.presentation.viewmodel.AddVisitationViewModel
import java.io.File
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
        onResult = {isSuccess: Boolean ->
            Log.d("AAA", isSuccess.toString())
            if (isSuccess) {
                Log.d("AAA", "isSuccess.toString()")
                viewModel.addFile(currentFile)
            }
        }
    )
    Scaffold(state = viewModel.uiState) { state ->
        LazyColumn() {
            item {
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
            }
            item {
                TextField(state = state.observation, onValueChange = viewModel::onObservationChange)
            }
            item {
                Button(text = "Add photo") {
                    currentFile = context.createImageFile().apply {
                        val uri = FileProvider.getUriForFile(context, "application_authority", this)
                        cameraLauncher.launch(uri)
                    }
                }
            }
            item {
                LazyRow() {
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
            }
        }
    }
}
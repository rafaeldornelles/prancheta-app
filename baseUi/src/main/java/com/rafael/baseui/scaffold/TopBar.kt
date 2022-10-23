package com.rafael.baseui.scaffold

import android.app.Activity
import android.graphics.drawable.VectorDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.rafael.baseui.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TopAppBar(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    title: String? = null,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    actions: List<TopAppBarAction> = listOf(
        TopAppBarAction("Options", Icons.Outlined.Settings) {
            coroutineScope.launch {
                scaffoldState.drawerState.open()
            }
        }
    )
) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val activity = LocalContext.current as? Activity
                IconButton(onClick = {
                    activity?.onBackPressed()
                }) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = null,
                    )
                }
                title?.let {
                    Text(text = title)
                }
            }

            actions.forEach {
                IconButton(onClick = it.onClick) {
                    Icon(imageVector = it.icon, contentDescription = it.label)
                }
            }
        }
    }
}

data class TopAppBarAction(
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)
package com.rafael.baseui.scaffold

import android.app.Activity
import android.graphics.drawable.VectorDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.rafael.baseui.R
import com.rafael.baseui.theme.spacing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TopAppBar(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    shouldShowBackButton: Boolean = false,
    title: @Composable () -> Unit = { DefaultTitle() } ,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    actions: List<TopAppBarAction> = listOf(
        TopAppBarAction("Options", Icons.Outlined.Settings) {
            coroutineScope.launch {
                scaffoldState.drawerState.open()
            }
        }
    )
) {
    Column {

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
                    if (shouldShowBackButton) {
                        IconButton(onClick = {
                            activity?.onBackPressed()
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBack,
                                contentDescription = null,
                            )
                        }
                    }
                    title()
                }

                actions.forEach {
                    IconButton(onClick = it.onClick) {
                        Icon(imageVector = it.icon, contentDescription = it.label)
                    }
                }
            }
        }
        Divider(Modifier.alpha(.5f))
    }
}

data class TopAppBarAction(
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@Composable
fun DefaultTitle() {
    Image(modifier = Modifier
        .fillMaxHeight(0.6f)
        .padding(horizontal = MaterialTheme.spacing.x200), painter = painterResource(id = R.drawable.prancheta_logo), contentDescription = "Prancheta")
}
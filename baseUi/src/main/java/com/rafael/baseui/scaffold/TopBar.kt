package com.rafael.baseui.scaffold

import android.app.Activity
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun TopAppBar(
    title: String? = null
) {
    androidx.compose.material.TopAppBar {
        Row {
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
    }
}
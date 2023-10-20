package com.rafael.baseui.scaffold

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rafael.baseui.theme.spacing
import com.rafael.core.cache.TokenCache
import org.koin.androidx.compose.get

@Composable
fun DefaultDrawer(
    tokenCache: TokenCache = get()
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                tokenCache.clear()
            }
            .padding(MaterialTheme.spacing.x300),
    ) {
        Icon(
            imageVector = Icons.Outlined.ExitToApp,
            contentDescription = null,
            tint = MaterialTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.x300))
        Text(text = "Logoff")
    }
}
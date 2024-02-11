package com.example.tolkachev.presentation.ui.screen.list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tolkachev.presentation.theme.AppTheme

@Composable
fun TopBar(
    title: String,
    query: String,
    searchEnabled: Boolean = false,
    onQueryChange: (String) -> Unit = {},
    onBack: () -> Unit = {},
    onSearch: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .height(64.dp)
    ) {
        AnimatedVisibility(
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterStart),
            visible = !searchEnabled
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        }
        AnimatedVisibility(
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterStart),
            visible = searchEnabled
        ) {
            IconButton(
                onClick = onBack,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        AnimatedVisibility(
            modifier = Modifier
                .padding(end = 16.dp)
                .align(Alignment.CenterEnd),
            visible = !searchEnabled
        ) {
            IconButton(
                onClick = onSearch,
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        AnimatedVisibility(
            modifier = Modifier
                .padding(start = 128.dp, end = 16.dp)
                .align(Alignment.CenterEnd)
                .fillMaxWidth(),
            visible = searchEnabled
        ) {
            TextField(
                value = query,
                singleLine = true,
                onValueChange = onQueryChange,
                textStyle = MaterialTheme.typography.bodyMedium,
                placeholder = {
                    Text(
                        text = "Поиск",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        TopBar(
            title = "Избранное",
            query = ""
        )
    }
}
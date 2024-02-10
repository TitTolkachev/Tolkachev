package com.example.tolkachev.presentation.ui.screen.list.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tolkachev.R
import com.example.tolkachev.presentation.theme.AppTheme

@Composable
fun RowScope.BottomNavButton(
    text: String,
    isActive: Boolean,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    val animatedButtonColor = animateColorAsState(
        targetValue =
        if (isActive) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.secondary,
        animationSpec = tween(200, 0, LinearEasing),
        label = "BottomNavButton color animation"
    )
    Button(
        modifier = Modifier.weight(1f),
        contentPadding = PaddingValues(16.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = animatedButtonColor.value,
            contentColor = if (isActive) MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.onSecondary
        ),
        onClick = onClick
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge.copy(color = Color.Unspecified)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview1() {
    AppTheme {
        Row {
            BottomNavButton(
                text = stringResource(id = R.string.movie_list_popular_movies_button_text),
                isActive = false
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview2() {
    AppTheme {
        Row {
            BottomNavButton(
                text = stringResource(id = R.string.movie_list_favourite_movies_button_text),
                isActive = true
            )
        }
    }
}

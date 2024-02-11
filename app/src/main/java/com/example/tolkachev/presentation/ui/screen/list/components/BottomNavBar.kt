package com.example.tolkachev.presentation.ui.screen.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tolkachev.R
import com.example.tolkachev.presentation.theme.AppTheme

@Composable
fun BottomNavBar(
    active: Int = 1,
    onFirstClick: () -> Unit = {},
    onSecondClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        BottomNavButton(
            text = stringResource(id = R.string.movie_list_popular_movies_button_text),
            isActive = active == 1,
            onClick = onFirstClick
        )
        BottomNavButton(
            text = stringResource(id = R.string.movie_list_favourite_movies_button_text),
            isActive = active == 2,
            onClick = onSecondClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        BottomNavBar()
    }
}

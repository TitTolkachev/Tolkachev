package com.example.tolkachev.presentation.ui.screen.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tolkachev.R
import com.example.tolkachev.presentation.theme.AppTheme

@Composable
fun BottomNavBar() {
    var activeButton by remember {
        mutableIntStateOf(1)
    }
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        BottomNavButton(
            text = stringResource(id = R.string.movie_list_popular_movies_button_text),
            isActive = activeButton == 1,
            onClick = { activeButton = 1 }
        )
        BottomNavButton(
            text = stringResource(id = R.string.movie_list_favourite_movies_button_text),
            isActive = activeButton == 2,
            onClick = { activeButton = 2 }
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

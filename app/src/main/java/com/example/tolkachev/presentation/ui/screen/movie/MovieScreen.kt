package com.example.tolkachev.presentation.ui.screen.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.tolkachev.R
import com.example.tolkachev.presentation.model.Movie
import com.example.tolkachev.presentation.theme.AppTheme

@Composable
fun MovieScreen(onBackClick: () -> Unit) {
    MovieContent(
        Movie(
            id = 0,
            name = "Изгой-один: Звёздные войны",
            description = "Сопротивление собирает отряд для выполнения особой миссии - надо выкрасть чертежи самого совершенного и мертоносного оружия Империи. Не всем суждено вернуться домой, но герои готовы к этому, ведь на кону судьба Галактики",
            genres = "фантастика, приключения",
            countries = "США"
        ),
        onBackClick = onBackClick
    )
}

@Composable
private fun MovieContent(movie: Movie, onBackClick: () -> Unit = {}) {
    BackButton(onClick = onBackClick)
    Column(Modifier.background(MaterialTheme.colorScheme.background)) {
        Image(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null
        )
        Column(modifier = Modifier.padding(horizontal = 32.dp)) {
            Spacer(Modifier.height(20.dp))
            Text(
                text = movie.name,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = AnnotatedString(movie.description),
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = AnnotatedString(movie.genres),
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = AnnotatedString(movie.countries),
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(Modifier.height(16.dp))
        }
        Spacer(Modifier.weight(1f))
    }
}

@Composable
private fun BackButton(onClick: () -> Unit) {
    IconButton(
        modifier = Modifier
            .zIndex(1f)
            .padding(vertical = 21.dp, horizontal = 16.dp),
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        MovieContent(
            Movie(
                id = 0,
                name = "Изгой-один: Звёздные войны",
                description = "Сопротивление собирает отряд для выполнения особой миссии - надо выкрасть чертежи самого совершенного и мертоносного оружия Империи. Не всем суждено вернуться домой, но герои готовы к этому, ведь на кону судьба Галактики",
                genres = "фантастика, приключения",
                countries = "США"
            )
        )
    }
}

package com.example.tolkachev.presentation.ui.screen.list.components

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tolkachev.R
import com.example.tolkachev.presentation.model.MovieShort
import com.example.tolkachev.presentation.theme.AppTheme
import com.example.tolkachev.presentation.theme.Black
import com.example.tolkachev.presentation.ui.util.advancedShadow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieCard(
    movie: MovieShort,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
) {
    val view = LocalView.current
    Surface(
        modifier = Modifier
            .advancedShadow(
                color = Black,
                alpha = 0.25F,
                cornersRadius = 16.dp,
                shadowBlurRadius = 10.dp,
                offsetY = 5.dp
            )
            .clip(RoundedCornerShape(16.dp))
            .combinedClickable(
                onClick = onClick,
                onLongClick = {
                    view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                    onLongClick()
                },
            ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(Modifier.height(IntrinsicSize.Min)) {
            AsyncImage(
                model = movie.posterUrl,
                modifier = Modifier
                    .padding(16.dp)
                    .size(40.dp, 63.dp)
                    .clip(RoundedCornerShape(5.dp)),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = movie.name ?: "Unknown",
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    if (movie.isFavourite) {
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            modifier = Modifier.size(18.dp),
                            painter = painterResource(id = R.drawable.ic_rating),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Spacer(modifier = Modifier.width(16.dp + 18.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                }
                Text(
                    text = movie.description ?: "",
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview1() {
    AppTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            MovieCard(
                MovieShort(0, "Movie 1", "Description", true)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview2() {
    AppTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            MovieCard(
                MovieShort(0, "Movie MovieMovie Movie Movie Movie", "Description", true)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview3() {
    AppTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            MovieCard(
                MovieShort(0, "Movie MovieMovie Movie Movie Movie", "Description", false)
            )
        }
    }
}
package com.example.tolkachev.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tolkachev.presentation.navigation.NavGraph
import com.example.tolkachev.presentation.navigation.Screen
import com.example.tolkachev.presentation.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
private fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        topBar = {},
        bottomBar = { BottomNavBar(navController) }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun BottomNavBar(navController: NavHostController) {
    val navBarItems = remember {
        listOf(
            Screen.Popular,
            Screen.Favourite
        )
    }
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = spacedBy(16.dp),
    ) {
        navBarItems.forEach {
            Button(
                modifier = Modifier.weight(1f),
                onClick = { navController.navigate(it.route) }
            ) {
                Text(text = it.title)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        MainScreen()
    }
}
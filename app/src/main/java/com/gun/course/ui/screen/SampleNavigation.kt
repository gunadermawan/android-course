package com.gun.course.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gun.course.ui.theme.CourseAppTheme

@Composable
fun SampleNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Home") {
        composable("Home") { HomeScreen(navController = navController) }
        composable("Detail") { DetailScreen(navController = navController) }
    }
}


@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Home Screen")
        Button(onClick = { navController.navigate("detail") }) {
            Text(text = "Go to Detail  Screen")
        }
    }
}

@Composable
fun DetailScreen(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Detail Screen")
        Button(onClick = { navController.navigate("home") }) {
            Text(text = "Go to Home Screen")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SampleNavigationPreview() {
    CourseAppTheme {
        SampleNavigation()
    }
}
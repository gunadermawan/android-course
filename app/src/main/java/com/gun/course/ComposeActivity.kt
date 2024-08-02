package com.gun.course

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gun.course.ui.components.BottomBar
import com.gun.course.ui.components.Screen
import com.gun.course.ui.theme.CourseAppTheme

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CourseAppTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailScreen.route) {
                BottomBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(Screen.Data.route) {
                DetailScreen(navController = navController)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navController = navController)
            }
        }
    }
}


@Preview
@Composable
private fun MyAppPreview() {
    CourseAppTheme {
        MyApp()
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

@Composable
fun ProfileScreen(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Profile Screen")
    }
}

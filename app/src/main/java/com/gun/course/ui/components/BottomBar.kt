package com.gun.course.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(navController: NavHostController, modifier: Modifier = Modifier) {
    NavigationBar(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem("Home", Icons.Default.Home, screen = Screen.Home),
            NavigationItem("Data", Icons.Default.List, screen = Screen.Data),
            NavigationItem("Profile", Icons.Default.AccountCircle, screen = Screen.Profile)
        )
        navigationItems.map { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                label = { Text(text = item.title) },
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) })
        }
    }
}
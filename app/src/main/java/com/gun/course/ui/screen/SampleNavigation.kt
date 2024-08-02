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

}



@Preview(showBackground = true)
@Composable
private fun SampleNavigationPreview() {
    CourseAppTheme {
        SampleNavigation()
    }
}
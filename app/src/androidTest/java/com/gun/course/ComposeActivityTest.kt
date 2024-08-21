package com.gun.course

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test

class ComposeActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun greetingScreenTest() {
        composeTestRule.setContent {
            GreetingScreen(modifier = Modifier)
        }

        composeTestRule.onNodeWithText("Enter your name").performTextInput("Andorid Developer")
        composeTestRule.onNodeWithText("Greet").performClick()
        composeTestRule.onNodeWithText("Hello, Andorid Developer").assertExists()
    }
}
package com.gun.course.ui.stateManagement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gun.course.ui.theme.CourseAppTheme


// parent compose
@Composable
fun ParentScreen(modifier: Modifier = Modifier) {
    var name by rememberSaveable {
        mutableStateOf("")
    }

    Column {
        Text(text = "Please input your name")
        FormInput(name = name, onNameChange = { name = it })
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Hello, $name!")
    }
}

// Child compose
@Composable
fun FormInput(name: String, onNameChange: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = name, onValueChange = { newName -> onNameChange(newName) },
        label = {
            Text(
                text = "Name"
            )
        }, modifier = Modifier.padding(8.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun ParentPreview() {
    CourseAppTheme {
        ParentScreen()
    }
}
package com.gun.course.ui.recomposition

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gun.course.ui.theme.CourseAppTheme

@Composable
fun Counter(modifier: Modifier = Modifier) {
    var count by remember {
        mutableStateOf(0)
    } // state compose

    Column {
        Text(text = "Count: $count")
        Button(onClick = { count++ }) {
            Text(text = "Increment")
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun CounterPreview() {
    CourseAppTheme {
        Counter()
    }

}
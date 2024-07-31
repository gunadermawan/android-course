package com.gun.course.ui.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gun.course.ui.theme.CourseAppTheme

@Composable
fun LazyColumnLayout(modifier: Modifier = Modifier) {
    val items = (1..100).toList() // sample data
    LazyColumn {
        item {
            Text(text = "Header")
        }
        items(items) {
            Text(
                text = "Item $it", modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
        item {
            Text(text = "Footer")
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun LazyLayoutPreview() {
    CourseAppTheme {
        LazyColumnLayout()
    }
}
package com.gun.course.ui.list.grid

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gun.course.R
import com.gun.course.ui.theme.CourseAppTheme

@Composable
fun LazyHorizontalGrid(modifier: Modifier = Modifier) {
    val items = (1..100).toList()
    val images = listOf(
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1,
        R.drawable.img_1
    )
    androidx.compose.foundation.lazy.grid.LazyHorizontalGrid(
        rows = GridCells.Fixed(3),
        modifier = modifier.padding(16.dp)
    ) {
        items(images) {
//            Text(
//                text = "Item $it", modifier = Modifier
//                    .fillMaxHeight()
//                    .padding(8.dp), style = MaterialTheme.typography.bodySmall
//            )
            Image(painter = painterResource(id = it), contentDescription = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LazyGridPreview() {
    CourseAppTheme {
        LazyHorizontalGrid()
    }
}
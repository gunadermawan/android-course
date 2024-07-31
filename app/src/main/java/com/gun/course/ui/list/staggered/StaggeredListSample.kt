package com.gun.course.ui.list.staggered

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gun.course.R
import com.gun.course.ui.theme.CourseAppTheme

@Composable
fun SampleStaggeredHorizontal(modifier: Modifier = Modifier) {
    val images = listOf(
        R.drawable.img_1,
        R.drawable.img_2,
        R.drawable.img_3,
        R.drawable.img_1,
        R.drawable.img_2,
        R.drawable.img_3,
        R.drawable.img_1,
        R.drawable.img_2,
        R.drawable.img_3,
        R.drawable.img_1,
        R.drawable.img_2,
        R.drawable.img_3,
        R.drawable.img_1,
        R.drawable.img_2,
        R.drawable.img_3,
        R.drawable.img_1,
        R.drawable.img_2,
        R.drawable.img_3,
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
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(120.dp),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(images) {
                Image(painter = painterResource(id = it), contentDescription = null)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun StaggeredPreview() {
    CourseAppTheme {
        SampleStaggeredHorizontal()
    }
}
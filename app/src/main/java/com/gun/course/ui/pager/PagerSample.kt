package com.gun.course.ui.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gun.course.R
import com.gun.course.ui.theme.CourseAppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerSample(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = {
        10
    })
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
    )
    HorizontalPager(state = pagerState) { page ->
//        pager content
        Box {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = images[page]),
                contentDescription = null
            )
            Text(text = "Page $page")
        }
    }
}

//Flow Row

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRowSample(modifier: Modifier = Modifier) {
    val tags = listOf(
        "Kotlin",
        "Compose",
        "Android",
        "FlowRow",
        "Layout",
        "Jetpack",
        "UI",
        "Design",
        "Development",
        "Accompanist"
    )
    FlowColumn {
        tags.forEach {
            TagItem(it)
        }
    }
}

@Composable
fun TagItem(tag: String, modifier: Modifier = Modifier) {
    AssistChip(
        modifier = Modifier.padding(4.dp),
        onClick = { },
        label = { Text(text = tag) },
        leadingIcon = {
            Icon(
                Icons.Filled.Settings,
                contentDescription = null,
                Modifier.size(AssistChipDefaults.IconSize)
            )
        })
}

@Preview(showBackground = true)
@Composable
private fun PagerSamplePreview() {
    CourseAppTheme {
        FlowRowSample()
    }
}
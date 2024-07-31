package com.gun.course.ui.basicLayouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gun.course.ui.theme.CourseAppTheme

@Composable
fun IntrinsicsSample(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .background(Color.Gray)
            .padding(16.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    ) {
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(50.dp)
                .background(Color.Red)
        )

        Spacer(modifier = modifier.width(8.dp))
        Text(
            text = "This is a text with intrinsic height",
            modifier = Modifier
                .background(Color.Blue)
                .align(Alignment.CenterVertically)
        )
    }
}

@Preview
@Composable
private fun SampleIntrinsicPreview() {
    CourseAppTheme {
        IntrinsicsSample()
    }
}
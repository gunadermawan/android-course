package com.gun.course.ui.basicLayouts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gun.course.R
import com.gun.course.ui.theme.CourseAppTheme

@Composable
fun ArtistColumns(modifier: Modifier = Modifier) {
    Row {
        Text(text = "Art Siloam")
        Text(text = "5 Minutes ago ")
    }
}

@Composable
fun ArtistCardRow(modifier: Modifier = Modifier) {
    Row {
        Image(
            modifier = Modifier.size(50.dp),
            painter = painterResource(id = R.drawable.img_1),
            contentDescription = null
        )
        Column {
            Text(text = "Art Siloam")
            Text(text = "5 Minutes ago ")
        }
    }
}

@Composable
fun ArtistAvatar(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.BottomEnd) {
        Image(
            modifier = Modifier.size(50.dp),
            painter = painterResource(id = R.drawable.img_1),
            contentDescription = null
        )
        Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(20.dp))
    }
}


@Preview(showBackground = true)
@Composable
private fun ContactCardPreview() {
    CourseAppTheme {
        ArtistAvatar()
    }
}
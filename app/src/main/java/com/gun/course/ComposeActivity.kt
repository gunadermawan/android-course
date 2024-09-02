package com.gun.course

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gun.course.ui.theme.CourseAppTheme

class ComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CourseAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TranslateScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun TranslateScreen(modifier: Modifier = Modifier) {
    var isEnglish by remember { mutableStateOf(true) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_1), contentDescription = stringResource(
                R.string.desc_img
            )
        )
        Text(
            text = if (isEnglish) stringResource(id = R.string.description_en) else stringResource(
                id = R.string.description_id
            )
        )
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (isEnglish) stringResource(id = R.string.switch_en) else stringResource(
                    id = R.string.switch_id
                )
            )
            Spacer(modifier = modifier.width(8.dp))
            Switch(checked = isEnglish, onCheckedChange = { isEnglish = it })
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SampleSharedElement(modifier: Modifier = Modifier) {
    SharedTransitionLayout {
        var isExpanded by remember { mutableStateOf(false) }
        var boundsTransform = { _: Rect, _: Rect -> tween<Rect>(550) }
        AnimatedContent(targetState = isExpanded) {
            if (!it) {
                Row(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(6.dp)
                        .clickable { isExpanded = !isExpanded }) {
                    Image(
                        modifier = modifier
                            .sharedElement(
                                state = rememberSharedContentState(key = "image"),
                                animatedVisibilityScope = this@AnimatedContent,
                                boundsTransform = boundsTransform
                            )
                            .size(130.dp),
                        painter = painterResource(id = R.drawable.img_1),
                        contentDescription = null
                    )
                    Text(
                        text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
                                "", fontSize = 12.sp,
                        modifier = modifier
                            .sharedElement(
                                state = rememberSharedContentState(key = "text"),
                                animatedVisibilityScope = this@AnimatedContent,
                                boundsTransform = boundsTransform
                            )
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            } else {
                Column(modifier = modifier
                    .fillMaxSize()
                    .clickable { isExpanded = !isExpanded }) {
                    Image(
                        modifier = modifier
                            .sharedElement(
                                state = rememberSharedContentState(key = "image"),
                                animatedVisibilityScope = this@AnimatedContent,
                                boundsTransform = boundsTransform
                            )
                            .size(320.dp),
                        painter = painterResource(id = R.drawable.img_1),
                        contentDescription = null
                    )
                    Text(
                        text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
                                "", fontSize = 12.sp,
                        modifier = modifier
                            .sharedElement(
                                state = rememberSharedContentState(key = "text"),
                                animatedVisibilityScope = this@AnimatedContent,
                                boundsTransform = boundsTransform
                            )
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

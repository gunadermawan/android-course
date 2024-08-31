package com.gun.course.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextDefaults
import androidx.glance.text.TextStyle

class MyAppWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                MyContent()
            }
        }
    }
}

@Composable
fun MyContent(modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "Where to?",
            style = TextStyle(TextDefaults.defaultTextColor),
            modifier = GlanceModifier.padding(12.dp)
        )
        Row(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(text = "Home", onClick = {})
            Button(text = "Work", onClick = {})
        }
    }
}
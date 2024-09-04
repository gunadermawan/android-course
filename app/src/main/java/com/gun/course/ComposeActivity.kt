package com.gun.course

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.gun.course.ui.theme.CourseAppTheme

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CourseAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ExoPlayerExample(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    @Composable
    fun ExoPlayerExample(modifier: Modifier = Modifier) {
        val context = LocalContext.current

        val exoPlayer = remember {
            ExoPlayer.Builder(context).build().apply {
                val mediaItem =
                    MediaItem.fromUri("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4")
                setMediaItem(mediaItem)
                prepare()
            }
        }

        var isPlaying by remember { mutableStateOf(false) }

        DisposableEffect(key1 = Unit) {
            onDispose {
                exoPlayer.release()
            }
        }
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AndroidView(factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                }
            }, modifier = modifier
                .fillMaxWidth()
                .height(300.dp))
            Spacer(modifier = modifier.height(16.dp))
            Button(onClick = {
                if (exoPlayer.isPlaying) {
                    exoPlayer.pause()
                } else {
                    exoPlayer.play()
                }
                isPlaying = exoPlayer.isPlaying
            }) {
                Text(text = if (isPlaying) "Pause" else "Play")
            }
        }
    }

    @Composable
    fun SoundPoolExample(modifier: Modifier = Modifier) {
        val context = LocalContext.current
        var soundPool by remember {
            mutableStateOf<SoundPool?>(null)
        }
        var soundId by remember { mutableIntStateOf(0) }

        LaunchedEffect(key1 = Unit) {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            soundPool = SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build()

            soundId = soundPool!!.load(context, R.raw.clinking_glasses, 1)
        }
        DisposableEffect(key1 = Unit) {
            onDispose {
                soundPool?.release()
                soundPool = null
            }
        }
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                soundPool?.play(soundId, 1f, 1f, 1, 0, 1f)
            }) {
                Text(text = "Mainkan suara klik")
            }

        }
    }

}
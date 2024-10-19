package com.gun.course

import android.os.Bundle
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.gun.course.ui.theme.CourseAppTheme
import com.gun.course.viewmodel.UserViewmodel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userViewmodel: UserViewmodel = getViewModel()
//        enableEdgeToEdge()
        setContent {
            CourseAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    EncryptScreen(
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }

    @Composable
    fun UserListScreen(modifier: Modifier = Modifier, viewmodel: UserViewmodel) {
        val users by viewmodel.users.collectAsState()
        val error by viewmodel.error.collectAsState()
        LaunchedEffect(Unit) {
            viewmodel.fetchUsers()
        }
        if (error != null) {
            Text(text = "error: $error")
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(users) {
                    Column(modifier = modifier.padding(16.dp)) {
                        Text(text = it.name, style = MaterialTheme.typography.titleMedium)
                        Text(text = it.email, style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }

    fun encryptAES(text: String, secretKey: String): String {
        val key: Key = SecretKeySpec(secretKey.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encryptedValue = cipher.doFinal(text.toByteArray())
        return Base64.encodeToString(encryptedValue, Base64.DEFAULT)
    }

    fun decryptAES(encryptedText: String, secretKey: String): String {
        val key: Key = SecretKeySpec(secretKey.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decodedValue = Base64.decode(encryptedText, Base64.DEFAULT)
        val decryptedValue = cipher.doFinal(decodedValue)
        return String(decryptedValue)
    }

    @Composable
    fun EncryptScreen(modifier: Modifier = Modifier) {
        var plainText by remember { mutableStateOf(TextFieldValue("")) }
        var encryptedText by remember { mutableStateOf("") }
        var decryptedText by remember { mutableStateOf("") }

        val secretKey = "1234567890123456"
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Masukan teks untuk dienkripsi:")
            Spacer(modifier = modifier.height(8.dp))
            TextField(
                value = plainText,
                onValueChange = { plainText = it },
                modifier = modifier.fillMaxWidth(),
                label = { Text(text = "Teks Asli") }
            )
            Spacer(modifier = modifier.height(8.dp))
            Button(onClick = { encryptedText = encryptAES(plainText.text, secretKey) }) {
                Text(text = "Enkripsi")
            }
            Spacer(modifier = modifier.height(8.dp))
            if (encryptedText.isNotEmpty()) {
                Text("Teks terenkripsi: $encryptedText")
                Spacer(modifier = modifier.height(8.dp))
                Button(onClick = { decryptedText = decryptAES(encryptedText, secretKey) }) {
                    Text(text = "Dekripsi")
                }
                Spacer(modifier = modifier.height(8.dp))
                if (decryptedText.isNotEmpty()) {
                    Text(text = "Teks Dekripsi: $decryptedText")
                }
            }
        }
    }
}
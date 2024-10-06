package com.gun.course.utils

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier


class CustomAlertDialog {
    @Composable
    fun customAlertDialog(modifier: Modifier = Modifier) {
        var showDialog by remember {
            mutableStateOf(true)
        }
        if (showDialog) {
            AlertDialog(onDismissRequest = {
                showDialog = false
            }, title = {
                Text(text = "Peringatan")
            }, text = {
                Text(text = "Apakah ingin melanjutkan proses?")
            }, confirmButton = {
                Button(onClick = {
                    showDialog = false
                }) {
                    Text(text = "Ya")
                }
            }, dismissButton = {
                Button(onClick = {
                    showDialog = false
                }) {
                    Text(text = "Tidak")
                }
            }
            )
        }
    }
}
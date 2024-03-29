package com.example.glasstask.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign

class TestingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestingScreen()
        }
    }
}

@Composable
fun TestingScreen() {
    Box(
        modifier = Modifier
        .fillMaxSize()
    ){
        Text(text = "Gaddar Kumar chaudhary", textAlign = TextAlign.Center, color = Color.White)
    }
}
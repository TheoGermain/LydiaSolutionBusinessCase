package com.example.lydiasolutionbusinesscase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.lydiasolutionbusinesscase.navigation.MainNavigation
import com.example.lydiasolutionbusinesscase.ui.theme.LydiaSolutionBusinessCaseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LydiaSolutionBusinessCaseTheme {
                MainNavigation()
            }
        }
    }
}

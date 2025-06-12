package com.example.lydia_solution_business_case

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lydia_solution_business_case.ui.contacts.ContactListScreen
import com.example.lydia_solution_business_case.ui.theme.LydiaSolutionBusinessCaseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      LydiaSolutionBusinessCaseTheme {
        ContactListScreen(
            viewModel = hiltViewModel(),
        )
      }
    }
  }
}

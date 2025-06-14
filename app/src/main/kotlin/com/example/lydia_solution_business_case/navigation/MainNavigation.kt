package com.example.lydia_solution_business_case.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lydia_solution_business_case.ui.contactDetails.ContactDetailsScreen
import com.example.lydia_solution_business_case.ui.contacts.ContactListScreen
import kotlinx.serialization.Serializable

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ContactList,
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it / 5 }) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it / 5 }) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) },
    ) {
        composable<ContactList> {
            ContactListScreen(
                viewModel = hiltViewModel(),
                navigateToDetails = { contactId ->
                    navController.navigate(ContactDetails(contactId))
                },
            )
        }
        composable<ContactDetails> {
            ContactDetailsScreen(
                viewModel = hiltViewModel(),
                goBack = navController::navigateUp,
            )
        }
    }
}

@Serializable
data object ContactList

@Serializable
data class ContactDetails(val contactId: String)
package com.example.lydiasolutionbusinesscase.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.example.lydiasolutionbusinesscase.ui.contactDetails.ContactDetailsScreen
import com.example.lydiasolutionbusinesscase.ui.contactDetails.ContactDetailsViewModel
import com.example.lydiasolutionbusinesscase.ui.contacts.ContactListScreen
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun MainNavigation() {
    val backStack = rememberNavBackStack(ContactList)
    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()

    NavDisplay(
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        sceneStrategy = listDetailStrategy,
        transitionSpec = {
            // Slide in from right when navigating forward
            slideInHorizontally(initialOffsetX = { it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { -it })
        },
        popTransitionSpec = {
            // Slide in from left when navigating back
            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { it })
        },
        predictivePopTransitionSpec = {
            // Slide in from left when navigating back
            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { it })
        },
        entryProvider = entryProvider {
            entry<ContactList>(
                metadata = ListDetailSceneStrategy.listPane(
                    /*detailPlaceholder = {
                        ContentYellow("Choose a product from the list")
                    }*/
                ),
            ) {
                ContactListScreen(
                    viewModel = hiltViewModel(),
                    navigateToDetails = { contactId -> backStack.add(ContactDetails(contactId = contactId)) },
                )
            }
            entry<ContactDetails>(
                metadata = ListDetailSceneStrategy.detailPane(),
            ) { key ->
                val viewModel = hiltViewModel<ContactDetailsViewModel, ContactDetailsViewModel.Factory>(
                    creationCallback = { factory ->
                        factory.create(key)
                    }
                )
                ContactDetailsScreen(
                    viewModel = viewModel,
                    goBack = { backStack.removeLastOrNull() },
                )
            }
        }
    )
}

@Serializable
data object ContactList : NavKey

@Serializable
data class ContactDetails(val contactId: String) : NavKey

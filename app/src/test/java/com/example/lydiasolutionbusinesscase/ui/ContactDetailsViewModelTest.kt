package com.example.lydiasolutionbusinesscase.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.lydiasolutionbusinesscase.domain.models.Contact
import com.example.lydiasolutionbusinesscase.domain.usecases.GetContactUseCase
import com.example.lydiasolutionbusinesscase.ui.contactDetails.ContactDetailsUiState
import com.example.lydiasolutionbusinesscase.ui.contactDetails.ContactDetailsViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ContactDetailsViewModelTest {
    private lateinit var viewModel: ContactDetailsViewModel
    private val getContactUseCase: GetContactUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()

    private val contactId = "id_1"
    private val contact = Contact(
        id = contactId,
        firstName = "John",
        lastName = "Doe",
        title = "Mr.",
        email = "john.doe@email.com",
        phone = "+1234567890",
        cell = "+11231487527",
        pictureUrl = "https://example.com/john.jpg",
        address = "742 Evergreen Terrace, Springfield, Illinois, USA"
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState emits Success when useCase returns contact`() = runTest {
        // GIVEN
        every { getContactUseCase(contactId) } returns flowOf(contact)

        val savedStateHandle = mockk<SavedStateHandle>()
        //savedStateHandle.mockkToRoute(ContactDetails(contactId = contactId))

        viewModel = ContactDetailsViewModel(savedStateHandle, getContactUseCase)

        // WHEN & THEN
        viewModel.uiState.test {
            assertEquals(ContactDetailsUiState.Loading, awaitItem())
            assertEquals(ContactDetailsUiState.Success(contact), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `uiState emits Error when useCase throws exception`() = runTest {
        // GIVEN
        every { getContactUseCase(contactId) } returns flow {
            throw RuntimeException("Failed")
        }

        val savedStateHandle = mockk<SavedStateHandle>()
       // savedStateHandle.mockkToRoute(ContactDetails(contactId = contactId))

        viewModel = ContactDetailsViewModel(savedStateHandle, getContactUseCase)

        // WHEN & THEN
        viewModel.uiState.test {
            assertEquals(ContactDetailsUiState.Loading, awaitItem())
            assertEquals(ContactDetailsUiState.Error, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}

/*
inline fun <reified T : Any> SavedStateHandle.mockkToRoute(route: T) {
    mockkStatic("androidx.navigation.SavedStateHandleKt")
    every { this@mockkToRoute.toRoute<T>() } returns route
}*/

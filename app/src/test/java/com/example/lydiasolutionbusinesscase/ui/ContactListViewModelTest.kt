package com.example.lydiasolutionbusinesscase.ui

import androidx.paging.PagingData
import app.cash.turbine.test
import com.example.lydiasolutionbusinesscase.domain.models.Contact
import com.example.lydiasolutionbusinesscase.domain.usecases.GetContactsUseCase
import com.example.lydiasolutionbusinesscase.ui.contacts.ContactListViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import androidx.paging.testing.asSnapshot

@OptIn(ExperimentalCoroutinesApi::class)
class ContactListViewModelTest {
    private val mockGetContactsUseCase: GetContactsUseCase = mockk()
    private lateinit var viewModel: ContactListViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val mockContact1 = Contact(
        id = "1",
        firstName = "John",
        lastName = "Doe",
        title = "Mr.",
        email = "john.doe@email.com",
        phone = "+1234567890",
        cell = "+11231487527",
        pictureUrl = "https://example.com/john.jpg",
        address = "123 Main St, Springfield",
    )

    private val mockContact2 = Contact(
        id = "2",
        firstName = "Jane",
        lastName = "Smith",
        title = "Ms.",
        email = "jane.smith@email.com",
        phone = "+0987654321",
        cell = "+10915284521",
        pictureUrl = "https://example.com/jane.jpg",
        address = "456 Elm St, Springfield",
    )

    private val mockContactsList = listOf(mockContact1, mockContact2)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Suppress("UnusedFlow")
    @Test
    fun `when viewModel is initialized, should call use case`() = runTest {
        // GIVEN
        every { mockGetContactsUseCase() } returns flowOf(PagingData.empty())

        // WHEN
        viewModel = ContactListViewModel(mockGetContactsUseCase)

        // THEN
        verify(exactly = 1) { mockGetContactsUseCase() }
    }

    @Test
    fun `when use case emits paging data, should expose it through contacts flow`() = runTest {
        // GIVEN
        val expectedPagingData = PagingData.from(mockContactsList)
        every { mockGetContactsUseCase() } returns flowOf(expectedPagingData)

        // WHEN
        viewModel = ContactListViewModel(mockGetContactsUseCase)
        testScheduler.advanceUntilIdle()

        // THEN
        viewModel.contacts.test {
            val actualPagingData = awaitItem()
            val actualSnapshot = actualPagingData.toSnapshot()
            assertEquals(mockContactsList, actualSnapshot)
            cancelAndIgnoreRemainingEvents()
        }
    }
}

private suspend fun <T : Any> PagingData<T>.toSnapshot(): List<T> {
    return flowOf(this).asSnapshot()
}
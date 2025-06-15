package com.example.lydiasolutionbusinesscase.data.repository

import app.cash.turbine.test
import com.example.lydiasolutionbusinesscase.data.api.ContactApi
import com.example.lydiasolutionbusinesscase.data.db.ContactDao
import com.example.lydiasolutionbusinesscase.data.db.ContactEntity
import com.example.lydiasolutionbusinesscase.data.db.LocalDatabase
import com.example.lydiasolutionbusinesscase.domain.models.Contact
import com.example.lydiasolutionbusinesscase.domain.repositories.ContactsRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ContactsRepositoryImplTest {
    private val mockDao: ContactDao = mockk()
    private val mockDb: LocalDatabase = mockk {
        every { contactDao() } returns mockDao
    }
    private val mockApi: ContactApi = mockk()
    private lateinit var repository: ContactsRepository

    private val entity = ContactEntity(
        id = "123",
        firstName = "Alice",
        lastName = "Durand",
        title = "Mme",
        email = "alice@email.com",
        phone = "0102030405",
        cell = "0601020304",
        pictureUrl = "https://example.com/alice.jpg",
        address = "123 rue de Paris",
        page = 1,
        indexInResponse = 0,
    )

    private val expectedContactModel = Contact(
        id = entity.id,
        firstName = entity.firstName,
        lastName = entity.lastName,
        title = entity.title,
        email = entity.email,
        phone = entity.phone,
        cell = entity.cell,
        pictureUrl = entity.pictureUrl,
        address = entity.address,
    )

    @Test
    fun `getContactById should return mapped Contact from DAO`() = runTest {
        // GIVEN
        repository = ContactsRepositoryImpl(api = mockApi, db = mockDb)
        every { mockDao.getContactById("123") } returns flowOf(entity)

        // WHEN & THEN
        repository.getContactById("123").test {
            val item = awaitItem()
            assertEquals(expectedContactModel, item)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
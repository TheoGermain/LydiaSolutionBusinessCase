package com.example.lydiasolutionbusinesscase.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lydiasolutionbusinesscase.data.db.ContactEntity
import com.example.lydiasolutionbusinesscase.data.db.LocalDatabase
import com.example.lydiasolutionbusinesscase.data.repository.fakes.FakeContactApi
import com.example.lydiasolutionbusinesscase.data.repository.fakes.FakeContactEntityFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalPagingApi::class)
@RunWith(AndroidJUnit4::class)
class ContactRemoteMediatorIntegrationTest {
    private lateinit var db: LocalDatabase
    private lateinit var fakeApi: FakeContactApi

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LocalDatabase::class.java
        ).allowMainThreadQueries().build()
        fakeApi = FakeContactApi()
    }

    @After
    fun tearDown() {
        db.close()
        Dispatchers.resetMain()
    }

    @Test
    fun initialize_shouldLaunchInitialRefresh_whenDatabaseIsEmpty() = runTest {
        val mediator = ContactRemoteMediator(db, fakeApi)

        val result = mediator.initialize()

        assertEquals(RemoteMediator.InitializeAction.LAUNCH_INITIAL_REFRESH, result)
    }

    @Test
    fun initialize_shouldSkipInitialRefresh_whenDatabaseIsNotEmpty() = runTest {
        db.contactDao().insertAll(
            listOf(
                ContactEntity(
                    id = "id_1",
                    firstName = "John",
                    lastName = "Doe",
                    title = "Mr.",
                    email = "john.doe@email.com",
                    phone = "+1234567890",
                    cell = "+11231487527",
                    pictureUrl = "https://example.com/john.jpg",
                    address = "123 Main St",
                    page = 1,
                    indexInResponse = 0
                )
            )
        )

        val mediator = ContactRemoteMediator(db, fakeApi)

        val result = mediator.initialize()

        assertEquals(RemoteMediator.InitializeAction.SKIP_INITIAL_REFRESH, result)
    }

    @Test
    fun load_append_shouldFetchNextPageAndInsertIntoDb() = runTest {
        val dao = db.contactDao()

        // GIVEN
        val existingContacts = listOf(
            FakeContactEntityFactory.create(page = 1, indexInResponse = 0),
            FakeContactEntityFactory.create(page = 1, indexInResponse = 1),
            FakeContactEntityFactory.create(page = 1, indexInResponse = 2),
        )
        dao.insertAll(existingContacts)

        val mediator = ContactRemoteMediator(db, fakeApi)

        val pagingState = PagingState<Int, ContactEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        // WHEN
        val result = mediator.load(LoadType.APPEND, pagingState)

        // THEN
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        val dbItemCount = dao.countItems()
        assertTrue(dbItemCount > existingContacts.size)

        val lastPageInDb = dao.getMaxPage()
        assertEquals(lastPageInDb, 2)
    }
}
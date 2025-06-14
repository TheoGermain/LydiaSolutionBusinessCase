package com.example.lydiasolutionbusinesscase.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.lydiasolutionbusinesscase.data.PAGE_SIZE
import com.example.lydiasolutionbusinesscase.data.api.ContactApi
import com.example.lydiasolutionbusinesscase.data.db.ContactEntity
import com.example.lydiasolutionbusinesscase.data.db.LocalDatabase
import com.example.lydiasolutionbusinesscase.data.db.toDomain
import com.example.lydiasolutionbusinesscase.domain.models.Contact
import com.example.lydiasolutionbusinesscase.domain.repositories.ContactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val api: ContactApi,
    private val db: LocalDatabase,
) : ContactsRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getContacts(): Flow<PagingData<Contact>> = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false,
        ),
        remoteMediator = ContactRemoteMediator(db, api),
        pagingSourceFactory = { db.contactDao().pagingSource() },
    ).flow.map { pagingData ->
        pagingData.map { entity -> entity.toDomain() }
    }

    override fun getContactById(id: String): Flow<Contact> =
        db.contactDao().getContactById(id).map(ContactEntity::toDomain)
}

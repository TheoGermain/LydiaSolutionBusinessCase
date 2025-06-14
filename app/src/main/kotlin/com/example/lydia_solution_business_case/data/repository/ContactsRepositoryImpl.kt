package com.example.lydia_solution_business_case.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.lydia_solution_business_case.data.PAGE_SIZE
import com.example.lydia_solution_business_case.data.api.ContactApi
import com.example.lydia_solution_business_case.data.db.ContactEntity
import com.example.lydia_solution_business_case.data.db.LocalDatabase
import com.example.lydia_solution_business_case.data.db.toDomain
import com.example.lydia_solution_business_case.domain.models.Contact
import com.example.lydia_solution_business_case.domain.repositories.ContactsRepository
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
        pagingSourceFactory = { db.contactDao().pagingSource() }
    ).flow.map { pagingData ->
        pagingData.map { entity -> entity.toDomain() }
    }

    override fun getContactById(id: String): Flow<Contact> =
        db.contactDao().getContactById(id).map(ContactEntity::toDomain)
}

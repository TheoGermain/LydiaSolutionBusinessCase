package com.example.lydiasolutionbusinesscase.domain.repositories

import androidx.paging.PagingData
import com.example.lydiasolutionbusinesscase.domain.models.Contact
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {
    fun getContacts(): Flow<PagingData<Contact>>

    fun getContactById(id: String): Flow<Contact>
}

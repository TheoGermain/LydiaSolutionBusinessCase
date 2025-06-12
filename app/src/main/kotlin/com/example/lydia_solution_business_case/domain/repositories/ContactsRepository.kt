package com.example.lydia_solution_business_case.domain.repositories

import com.example.lydia_solution_business_case.domain.models.Contact
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {
    fun getContacts(): Flow<List<Contact>>
}

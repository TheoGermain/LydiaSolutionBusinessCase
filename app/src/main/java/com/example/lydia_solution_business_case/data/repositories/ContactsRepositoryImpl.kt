package com.example.lydia_solution_business_case.data.repositories

import com.example.lydia_solution_business_case.data.datasources.MockContactsDataSource
import com.example.lydia_solution_business_case.domain.models.Contact
import com.example.lydia_solution_business_case.domain.repositories.ContactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor() : ContactsRepository {
  override fun getContacts(): Flow<List<Contact>> = flow {
    emit(MockContactsDataSource.getMockContacts())
  }
}

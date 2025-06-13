package com.example.lydia_solution_business_case.data.repository

import com.example.lydia_solution_business_case.data.api.ContactApi
import com.example.lydia_solution_business_case.data.remote.model.ContactDto
import com.example.lydia_solution_business_case.data.remote.model.toDomain
import com.example.lydia_solution_business_case.domain.models.Contact
import com.example.lydia_solution_business_case.domain.repositories.ContactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val contactApi: ContactApi,
) : ContactsRepository {
  override fun getContacts(): Flow<List<Contact>> = flow {
    emit(contactApi.getContacts().results.map(ContactDto::toDomain))
  }
}

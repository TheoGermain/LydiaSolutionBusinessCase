package com.example.lydia_solution_business_case.domain.usecases

import androidx.paging.PagingData
import com.example.lydia_solution_business_case.domain.models.Contact
import com.example.lydia_solution_business_case.domain.repositories.ContactsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetContactsUseCase @Inject constructor(
    private val repository: ContactsRepository
) {
    operator fun invoke(): Flow<PagingData<Contact>> = repository.getContacts()
}
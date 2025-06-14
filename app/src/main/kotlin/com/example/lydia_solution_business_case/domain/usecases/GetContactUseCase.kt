package com.example.lydia_solution_business_case.domain.usecases

import com.example.lydia_solution_business_case.domain.models.Contact
import com.example.lydia_solution_business_case.domain.repositories.ContactsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetContactUseCase @Inject constructor(
    private val repository: ContactsRepository
) {
    operator fun invoke(id: String): Flow<Contact> = repository.getContactById(id)
}
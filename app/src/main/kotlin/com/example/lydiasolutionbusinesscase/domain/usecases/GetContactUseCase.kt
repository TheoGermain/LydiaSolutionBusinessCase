package com.example.lydiasolutionbusinesscase.domain.usecases

import com.example.lydiasolutionbusinesscase.domain.models.Contact
import com.example.lydiasolutionbusinesscase.domain.repositories.ContactsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetContactUseCase @Inject constructor(
    private val repository: ContactsRepository,
) {
    operator fun invoke(id: String): Flow<Contact> = repository.getContactById(id)
}

package com.example.lydiasolutionbusinesscase.domain.usecases

import androidx.paging.PagingData
import com.example.lydiasolutionbusinesscase.domain.models.Contact
import com.example.lydiasolutionbusinesscase.domain.repositories.ContactsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetContactsUseCase @Inject constructor(
    private val repository: ContactsRepository,
) {
    operator fun invoke(): Flow<PagingData<Contact>> = repository.getContacts()
}

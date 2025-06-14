package com.example.lydia_solution_business_case.ui.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.lydia_solution_business_case.domain.models.Contact
import com.example.lydia_solution_business_case.domain.repositories.ContactsRepository
import com.example.lydia_solution_business_case.domain.usecases.GetContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(
    getContactsUseCase: GetContactsUseCase,
) : ViewModel() {
    val contacts: Flow<PagingData<Contact>> = getContactsUseCase().cachedIn(viewModelScope)
}

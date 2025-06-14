package com.example.lydia_solution_business_case.ui.contactDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.lydia_solution_business_case.domain.models.Contact
import com.example.lydia_solution_business_case.domain.repositories.ContactsRepository
import com.example.lydia_solution_business_case.navigation.ContactDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ContactDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: ContactsRepository,
) : ViewModel() {

    private val contactId = savedStateHandle.toRoute<ContactDetails>().contactId

    val uiState = repository.getContactById(contactId).map {
        ContactDetailsUiState.Success(it)
    }
        .stateIn(viewModelScope, initialValue = ContactDetailsUiState.Loading, started = WhileSubscribed(5000))
}

sealed class ContactDetailsUiState {
    data class Success(val contact: Contact) : ContactDetailsUiState()
    data class Error(val message: String) : ContactDetailsUiState()
    data object Loading : ContactDetailsUiState()
}
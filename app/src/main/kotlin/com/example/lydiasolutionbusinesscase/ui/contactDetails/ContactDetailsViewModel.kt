package com.example.lydiasolutionbusinesscase.ui.contactDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.lydiasolutionbusinesscase.domain.models.Contact
import com.example.lydiasolutionbusinesscase.domain.usecases.GetContactUseCase
import com.example.lydiasolutionbusinesscase.navigation.ContactDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ContactDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getContactUseCase: GetContactUseCase,
) : ViewModel() {

    private val contactId = savedStateHandle.toRoute<ContactDetails>().contactId

    val uiState: StateFlow<ContactDetailsUiState> =
        getContactUseCase(contactId).map<Contact, ContactDetailsUiState> { contact ->
            ContactDetailsUiState.Success(contact)
        }.catch { exception ->
            emit(ContactDetailsUiState.Error)
        }
            .stateIn(
                viewModelScope,
                initialValue = ContactDetailsUiState.Loading,
                started = WhileSubscribed(5000),
            )
}

sealed class ContactDetailsUiState {
    data class Success(val contact: Contact) : ContactDetailsUiState()
    data object Error : ContactDetailsUiState()
    data object Loading : ContactDetailsUiState()
}

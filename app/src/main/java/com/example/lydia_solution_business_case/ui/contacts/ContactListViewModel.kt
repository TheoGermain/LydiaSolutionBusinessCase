package com.example.lydia_solution_business_case.ui.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lydia_solution_business_case.domain.models.Contact
import com.example.lydia_solution_business_case.domain.repositories.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val repository: ContactsRepository,
    @Named("IO") val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
  private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
  val contacts: StateFlow<List<Contact>> = _contacts.asStateFlow()

  init {
    viewModelScope.launch(ioDispatcher) {
      repository.getContacts().collect { _contacts.value = it }
    }
  }
}

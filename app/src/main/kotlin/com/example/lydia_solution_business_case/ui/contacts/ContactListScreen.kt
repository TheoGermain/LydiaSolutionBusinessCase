package com.example.lydia_solution_business_case.ui.contacts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lydia_solution_business_case.ui.components.ContactListItem

@Composable
fun ContactListScreen(
    viewModel: ContactListViewModel,
    modifier: Modifier = Modifier,
) {
  val contacts by viewModel.contacts.collectAsStateWithLifecycle()

  LazyColumn(modifier = modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
    items(contacts) { contact -> ContactListItem(contact = contact) }
  }
}

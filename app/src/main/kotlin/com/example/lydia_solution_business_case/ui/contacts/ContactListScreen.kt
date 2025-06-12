package com.example.lydia_solution_business_case.ui.contacts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lydia_solution_business_case.R
import com.example.lydia_solution_business_case.ui.components.ContactListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(
    viewModel: ContactListViewModel,
) {
  val contacts by viewModel.contacts.collectAsStateWithLifecycle()

  Scaffold(
      topBar = {
        TopAppBar(
            title = { Text(stringResource(R.string.contacts)) },
        )
      }) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxSize(),
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
              items(contacts) { contact -> ContactListItem(contact = contact) }
            }
      }
}

package com.example.lydiasolutionbusinesscase.ui.contacts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.lydiasolutionbusinesscase.R
import com.example.lydiasolutionbusinesscase.domain.models.Contact
import com.example.lydiasolutionbusinesscase.ui.components.ContactListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(
    viewModel: ContactListViewModel,
    navigateToDetails: (String) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val contacts = viewModel.contacts.collectAsLazyPagingItems()

    LaunchedEffect(contacts.loadState.append) {
        if (contacts.loadState.append is LoadState.Error) {
            val result = snackbarHostState.showSnackbar(
                message = context.getString(R.string.append_contacts_error),
                actionLabel = context.getString(R.string.retry),
            )
            if (result == SnackbarResult.ActionPerformed) {
                contacts.retry()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.contacts)) },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        when (contacts.loadState.refresh) {
            is LoadState.Loading -> ContactListLoadingScreen()
            is LoadState.Error -> ContactListErrorScreen(
                onRetry = { contacts.retry() },
            )

            is LoadState.NotLoading -> ContactListContent(
                contacts = contacts,
                contentPadding = innerPadding,
                navigateToDetails = navigateToDetails,
            )
        }
    }
}

@Composable
private fun ContactListErrorScreen(
    onRetry: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(stringResource(R.string.append_contacts_error))
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = onRetry,
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = stringResource(R.string.retry),
            )
        }
    }
}

@Composable
private fun ContactListLoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
        Spacer(Modifier.height(16.dp))
        Text(text = stringResource(R.string.loading_contacts))
    }
}

@Composable
private fun ContactListContent(
    contacts: LazyPagingItems<Contact>,
    contentPadding: PaddingValues,
    navigateToDetails: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        contentPadding = contentPadding,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            contacts.itemCount,
            key = contacts.itemKey { it.id },
        ) { index ->
            contacts[index]?.let { contact ->
                ContactListItem(
                    modifier = Modifier.clickable { navigateToDetails(contact.id) },
                    contact = contact,
                )
            }
        }

        item {
            if (contacts.loadState.append is LoadState.Loading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            } else {
                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }
}

package com.example.lydia_solution_business_case.ui.contactDetails

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.lydia_solution_business_case.R
import com.example.lydia_solution_business_case.domain.models.Contact
import com.example.lydia_solution_business_case.ui.components.ContactInfoCard
import androidx.core.net.toUri
import com.example.lydia_solution_business_case.ui.components.QuickActionButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailsScreen(
    viewModel: ContactDetailsViewModel,
    goBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {},
            navigationIcon = {
                IconButton(onClick = goBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )
    }) { innerPadding ->
        when (val state = uiState) {
            is ContactDetailsUiState.Loading -> {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is ContactDetailsUiState.Error -> {
                Box(contentAlignment = Alignment.Center) {
                    Text(state.message)
                }
            }

            is ContactDetailsUiState.Success -> {
                val contact = (uiState as ContactDetailsUiState.Success).contact
                ContactDetailsContent(contact = contact, modifier = Modifier.padding(innerPadding))
            }
        }
    }
}

@Composable
private fun ContactDetailsContent(
    contact: Contact,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        AsyncImage(
            model = contact.pictureUrl,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(148.dp)
                .clip(CircleShape)
        )
        Text(contact.fullName, style = MaterialTheme.typography.titleLarge)
        QuickActionsRow(
            phone = contact.phone,
            email = contact.email
        )
        ContactInfoCard(
            phone = contact.phone,
            cell = contact.cell,
            email = contact.email
        )
        ContactAddressCard(
            address = contact.address,
        )
    }
}

@Composable
private fun QuickActionsRow(
    phone: String,
    email: String,
) {
    val context = LocalContext.current
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        QuickActionButton(
            icon = {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Outlined.Phone,
                    contentDescription = stringResource(R.string.call),
                )
            },
            description = stringResource(R.string.call),
            onClick = {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = "tel:$phone".toUri()
                }
                context.startActivity(intent)
            }
        )
        Spacer(Modifier.width(24.dp))
        QuickActionButton(
            icon = {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Outlined.Email,
                    contentDescription = stringResource(R.string.email),
                )
            },
            description = stringResource(R.string.email),
            onClick = {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = "mailto:$email".toUri()
                }
                context.startActivity(intent)
            }
        )
    }
}

@Composable
fun ContactAddressCard(
    address: String,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = stringResource(R.string.address),
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = address)
        }
    }
}

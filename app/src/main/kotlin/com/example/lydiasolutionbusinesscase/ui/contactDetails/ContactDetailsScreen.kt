package com.example.lydiasolutionbusinesscase.ui.contactDetails

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.lydiasolutionbusinesscase.R
import com.example.lydiasolutionbusinesscase.domain.models.Contact
import com.example.lydiasolutionbusinesscase.ui.components.ContactAddressCard
import com.example.lydiasolutionbusinesscase.ui.components.ContactInfoCard
import com.example.lydiasolutionbusinesscase.ui.components.QuickActionButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailsScreen(
    viewModel: ContactDetailsViewModel,
    goBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                val state = uiState
                if (state is ContactDetailsUiState.Success) {
                    Text(
                        text = "${state.contact.firstName} ${state.contact.lastName}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = goBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                    )
                }
            },
        )
    }) { innerPadding ->
        when (val state = uiState) {
            is ContactDetailsUiState.Loading -> ContactDetailsLoadingScreen()

            is ContactDetailsUiState.Error -> ContactDetailsErrorScreen()

            is ContactDetailsUiState.Success -> ContactDetailsContent(
                contact = state.contact,
                modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
            )
        }
    }
}

@Composable
private fun ContactDetailsLoadingScreen() {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ContactDetailsErrorScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(stringResource(R.string.generic_error), textAlign = TextAlign.Center)
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
            .padding(horizontal = 16.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        AsyncImage(
            model = contact.pictureUrl,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(148.dp)
                .clip(CircleShape),
        )
        Text(contact.fullName, style = MaterialTheme.typography.titleLarge)
        QuickActionsRow(
            phone = contact.phone,
            email = contact.email,
        )
        ContactInfoCard(
            phone = contact.phone,
            cell = contact.cell,
            email = contact.email,
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
        modifier = Modifier.fillMaxWidth(),
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
            },
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
            },
        )
    }
}

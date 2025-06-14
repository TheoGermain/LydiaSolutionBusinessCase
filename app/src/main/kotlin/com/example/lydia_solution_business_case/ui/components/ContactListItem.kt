package com.example.lydia_solution_business_case.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.lydia_solution_business_case.domain.models.Contact

@Composable
fun ContactListItem(
    contact: Contact,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = Modifier.semantics(mergeDescendants = true) {
            role = Role.Button
        }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = contact.pictureUrl,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(contact.fullName, style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Preview
@Composable
private fun ContactListItemPreview() {
    ContactListItem(
        contact =
            Contact(
                id = "1",
                title = "Mr.",
                firstName = "John",
                lastName = "Doe",
                pictureUrl = "https://example.com/john_doe.jpg",
                address = "123 Main St, Springfield",
                email = "john.doe@email.com",
                phone = "123-456-7890",
                cell = "987-654-3210",
            ),
    )
}

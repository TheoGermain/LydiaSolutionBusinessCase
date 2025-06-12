package com.example.lydia_solution_business_case.ui.components

import android.util.Log
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.lydia_solution_business_case.domain.models.Contact

@Composable
fun ContactListItem(
    contact: Contact,
    modifier: Modifier = Modifier,
) {
    Card {
        Row(modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            AsyncImage(
                model = contact.pictureUrl,
                onError = {
                    Log.d("ContactListItem", "Error loading image for contact: ${contact.fullName} ($it)")
                },
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(contact.fullName, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview
@Composable
private fun ContactListItemPreview() {
  ContactListItem(
      contact =
          Contact(
              udid = "1",
              title = "Mr.",
              firstName = "John",
              lastName = "Doe",
              pictureUrl = "https://example.com/john_doe.jpg",
          ),
  )
}

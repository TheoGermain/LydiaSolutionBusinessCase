package com.example.lydiasolutionbusinesscase.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lydiasolutionbusinesscase.R
import com.example.lydiasolutionbusinesscase.ui.theme.LydiaSolutionBusinessCaseTheme

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
                style = MaterialTheme.typography.titleMedium,
            )
            Text(text = address)
        }
    }
}

@Preview
@Composable
private fun ContactAddressCardPreview() {
    LydiaSolutionBusinessCaseTheme {
        ContactAddressCard(
            address = "123 Main St, Springfield, USA",
        )
    }
}

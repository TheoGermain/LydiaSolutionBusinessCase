package com.example.lydia_solution_business_case.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lydia_solution_business_case.R
import com.example.lydia_solution_business_case.ui.theme.LydiaSolutionBusinessCaseTheme

@Composable
fun ContactInfoCard(
    phone: String,
    cell: String,
    email: String,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),

            ) {
            Text(
                text = stringResource(R.string.contact_info),
                style = MaterialTheme.typography.titleMedium
            )
            ContactInfoRow(
                icon = {
                    Icon(
                        Icons.Outlined.Phone,
                        contentDescription = null,
                    )
                },
                description = R.string.phone,
                text = phone,
            )
            ContactInfoRow(
                icon = {
                    Icon(
                        Icons.Outlined.Phone,
                        contentDescription = null,
                    )
                },
                description = R.string.cell,
                text = cell,
            )
            ContactInfoRow(
                icon = {
                    Icon(
                        Icons.Outlined.Email,
                        contentDescription = null,
                    )
                },
                description = R.string.email,
                text = email,
            )
        }
    }
}

@Preview
@Composable
private fun ContactInfoCardPreview() {
    LydiaSolutionBusinessCaseTheme {
        ContactInfoCard(
            phone = "123-456-7890",
            cell = "098-765-4321",
            email = "john.doe@email.com"
        )
    }
}
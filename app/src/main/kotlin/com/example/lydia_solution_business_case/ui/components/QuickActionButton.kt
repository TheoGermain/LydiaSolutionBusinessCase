package com.example.lydia_solution_business_case.ui.components

import android.R.attr.contentDescription
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lydia_solution_business_case.R
import com.example.lydia_solution_business_case.ui.theme.LydiaSolutionBusinessCaseTheme

@Composable
fun QuickActionButton(
    icon: @Composable () -> Unit,
    description: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier.clearAndSetSemantics {
            role = Role.Button
            contentDescription = description
        },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        IconButton(
            modifier = Modifier
                .size(64.dp),
            onClick = onClick,
            colors = IconButtonDefaults.iconButtonColors()
                .copy(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary,
                )
        ) {
            icon()
        }
        Text(description)
    }
}

@Preview
@Composable
private fun QuickActionButtonPreview() {
    LydiaSolutionBusinessCaseTheme {
        QuickActionButton(
            icon = {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Outlined.Email,
                    contentDescription = stringResource(R.string.email),
                )
            },
            description = "Mail",
            onClick = {}
        )
    }
}
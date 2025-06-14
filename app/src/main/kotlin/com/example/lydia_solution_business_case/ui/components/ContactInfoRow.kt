package com.example.lydia_solution_business_case.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lydia_solution_business_case.R
import com.example.lydia_solution_business_case.ui.theme.LydiaSolutionBusinessCaseTheme

@Composable
fun ContactInfoRow(
    icon: @Composable () -> Unit,
    @StringRes description: Int,
    text: String,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        icon()
        Spacer(Modifier.width(12.dp))
        Column {
            Text(text = text)
            Text(text = stringResource(description))
        }
    }
}

@Preview
@Composable
private fun ContactInfoRowPreview() {
    LydiaSolutionBusinessCaseTheme {
        ContactInfoRow(
            icon = {
                Icon(
                    Icons.Outlined.Phone,
                    contentDescription = null,
                )
            },
            description = R.string.phone,
            text = "John Doe"
        )
    }
}
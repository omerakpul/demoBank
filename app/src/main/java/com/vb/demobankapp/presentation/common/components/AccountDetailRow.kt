package com.vb.demobankapp.presentation.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.vb.demobankapp.presentation.common.ui.theme.TextDark
import com.vb.demobankapp.presentation.common.ui.theme.TextPlaceholder

@Composable
fun AccountDetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = TextPlaceholder,
            fontSize = 14.sp
        )
        Text(
            text = value,
            color = TextDark,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
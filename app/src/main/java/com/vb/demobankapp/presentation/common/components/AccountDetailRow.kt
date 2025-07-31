package com.vb.demobankapp.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vb.demobankapp.presentation.common.ui.theme.PrimaryYellow
import com.vb.demobankapp.presentation.common.ui.theme.TextDark
import com.vb.demobankapp.presentation.common.ui.theme.TextPlaceholder

@Composable
fun AccountDetailRow(
    label: String,
    value: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(PrimaryYellow, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("#", color = TextDark, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(label, fontWeight = FontWeight.Bold, color = TextDark)
            Text(value, color = TextPlaceholder)
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}
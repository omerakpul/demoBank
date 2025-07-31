package com.vb.demobankapp.presentation.ui.transfers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vb.demobankapp.domain.model.AccountInfo
import com.vb.demobankapp.presentation.common.components.AccountCard
import com.vb.demobankapp.presentation.common.ui.theme.*

@Composable
fun TransferScreen(
    onBackClick: () -> Unit,
    onAccountSelectClick: () -> Unit,
    onTransferClick: () -> Unit
) {
    var selectedAccount by remember { mutableStateOf<AccountInfo?>(null) }
    var recipientIban by remember { mutableStateOf("") }
    var recipientName by remember { mutableStateOf("") }
    var recipientSurname by remember { mutableStateOf("") }
    var transferAmount by remember { mutableStateOf("") }
    var showRecipientFields by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
            .padding(16.dp)
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = TextDark
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Para Transferi",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Hesap Seçimi
        Text(
            text = "Hesap Seç",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Hesap seçimi için tıklanabilir alan
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onAccountSelectClick() },
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            if (selectedAccount != null) {
                // Seçili hesap göster
                AccountCard(
                    accountName = selectedAccount!!.accountName,
                    accountNumber = selectedAccount!!.iban,
                    balance = "₺ ${selectedAccount!!.balance}",
                    onClick = { onAccountSelectClick() }
                )
            } else {
                // Hesap seç uyarısı
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Transfer edeceğiniz hesabı seçin",
                        color = TextPlaceholder
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Alıcı IBAN
        Text(
            text = "Alıcı IBAN",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = recipientIban,
            onValueChange = {
                recipientIban = it
                // IBAN girildiğinde alıcı alanlarını göster
                if (it.length >= 10) {
                    showRecipientFields = true
                } else {
                    showRecipientFields = false
                }
            },
            placeholder = { Text("TRXX XXXX XXXX XXXX XXXX XXXX XX") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        )

        // Alıcı bilgileri (IBAN girildiğinde görünür)
        if (showRecipientFields) {
            Spacer(modifier = Modifier.height(16.dp))

            // Alıcı Adı
            Text(
                text = "Alıcı Adı",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = recipientName,
                onValueChange = { recipientName = it },
                placeholder = { Text("Ö*** E*** AK***") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Alıcı Soyadı
            Text(
                text = "Alıcı Soyadı",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = recipientSurname,
                onValueChange = { recipientSurname = it },
                placeholder = { Text("Ö*** E*** AK***") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Transfer Miktarı
            Text(
                text = "Transfer Miktarı",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = transferAmount,
                onValueChange = { transferAmount = it },
                placeholder = { Text("0,00 TL") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Transfer Butonu
        Button(
            onClick = onTransferClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryYellow),
            shape = RoundedCornerShape(12.dp),
            enabled = selectedAccount != null && recipientIban.isNotEmpty() &&
                    recipientName.isNotEmpty() && recipientSurname.isNotEmpty() &&
                    transferAmount.isNotEmpty()
        ) {
            Text(
                text = "Transferi Onayla",
                color = TextDark,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransferScreenPreview() {
    TransferScreen(
        onBackClick = {},
        onAccountSelectClick = {},
        onTransferClick = {}
    )
}
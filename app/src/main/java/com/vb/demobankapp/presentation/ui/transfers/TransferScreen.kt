package com.vb.demobankapp.presentation.ui.transfers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vb.demobankapp.presentation.common.components.AccountCard
import com.vb.demobankapp.presentation.common.ui.theme.BackgroundCream
import com.vb.demobankapp.presentation.common.ui.theme.PrimaryYellow
import com.vb.demobankapp.presentation.common.ui.theme.TextDark

@Composable
fun TransferScreen(
    onBackClick: () -> Unit,
    onTransferClick: () -> Unit,
    viewModel: TransferViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val selectedAccount by viewModel.selectedAccount.collectAsState()
    val recipientIban by viewModel.recipientIban.collectAsState()
    val transferAmount by viewModel.transferAmount.collectAsState()
    val availableAccounts by viewModel.availableAccounts.collectAsState()
    var showRecipientFields by remember { mutableStateOf(false) }

    // State değişikliklerini dinle
    LaunchedEffect(state) {
        when (state) {
            is TransferState.Success -> {
                onTransferClick()
                viewModel.resetState()
            }
            // Error durumunda resetState çağırmıyoruz, kullanıcı görebilsin
            else -> {}
        }
    }

    LaunchedEffect(recipientIban) {
        showRecipientFields = recipientIban.length == 22
    }

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

        if (state is TransferState.Loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = PrimaryYellow)
            }
            return
        }

        // Hata mesajını göster
        if (state is TransferState.Error) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = (state as TransferState.Error).message,
                        color = Color(0xFFD32F2F),
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { viewModel.resetState() },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Text(
                            text = "×",
                            color = Color(0xFFD32F2F),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Hesap Seçimi Başlığı
        Text(
            text = "Hesap Seç",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Hesaplar yatayda listeleniyor
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            availableAccounts.forEach { account ->
                Card(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .width(200.dp)
                        .clickable { viewModel.selectAccount(account) },
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedAccount?.accountId == account.accountId) PrimaryYellow else Color.White
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    AccountCard(
                        accountName = account.accountName,
                        accountNumber = account.iban,
                        balance = "₺ ${account.balance}",
                        onClick = { viewModel.selectAccount(account) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Seçili Hesap Kartı
        if (selectedAccount != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                AccountCard(
                    accountName = selectedAccount!!.accountName,
                    accountNumber = selectedAccount!!.iban,
                    balance = "₺ ${selectedAccount!!.balance}",
                    onClick = {}
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

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
            onValueChange = { viewModel.updateRecipientIban(it) },
            placeholder = { Text("XXXX XXXX XXXX XXXX XXXX XX") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            prefix = { Text("TR38 ", color = TextDark, fontWeight = FontWeight.Bold) }
        )

        if (showRecipientFields) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Transfer Miktarı",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = transferAmount,
                onValueChange = { viewModel.updateTransferAmount(it) },
                placeholder = { Text("0,00 TL") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                suffix = { Text(" TL", color = TextDark) }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { viewModel.transferMoney() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryYellow),
            shape = RoundedCornerShape(12.dp),
            enabled = selectedAccount != null && recipientIban.length == 22 &&
                    transferAmount.isNotEmpty() && state !is TransferState.Loading
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
        onTransferClick = {}
    )
}
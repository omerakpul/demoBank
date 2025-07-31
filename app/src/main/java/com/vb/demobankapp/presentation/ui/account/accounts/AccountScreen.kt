package com.vb.demobankapp.presentation.ui.account.accounts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vb.demobankapp.domain.model.AccountInfo
import com.vb.demobankapp.presentation.common.components.AccountDetailRow
import com.vb.demobankapp.presentation.common.ui.theme.*

@Composable
fun AccountScreen(
    account: AccountInfo,
    onBackClick: () -> Unit,
    onUpdateAccountName: (String) -> Unit,
    onDeleteAccount: () -> Unit
) {
    var showEdit by remember { mutableStateOf(false) }
    var newAccountName by remember { mutableStateOf(account.accountName) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    var currentAccountName by remember { mutableStateOf(account.accountName) }

    LaunchedEffect(account.accountName) {
        currentAccountName = account.accountName
        newAccountName = account.accountName
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
            .padding(16.dp)
    ) {
        // Geri butonu ve başlık
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
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Hesap Detayları",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Hesap Bilgileri",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tekrar eden satırlar component ile değiştirildi:
        AccountDetailRow(label = "Hesap Adı", value = currentAccountName)
        AccountDetailRow(label = "Hesap Numarası", value = account.accountNumber)
        AccountDetailRow(label = "Bakiye", value = "₺ ${account.balance}")
        AccountDetailRow(label = "Hesap Türü", value = account.accountType)
        AccountDetailRow(label = "IBAN", value = account.iban)

        Spacer(modifier = Modifier.weight(1f))

        // Hesap Adı Değiştir ve input
        if (showEdit) {
            OutlinedTextField(
                value = newAccountName,
                onValueChange = { newAccountName = it },
                label = { Text("Yeni Hesap Adı") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    onUpdateAccountName(newAccountName)
                    currentAccountName = newAccountName  // UI'ı hemen güncelle
                    showEdit = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryYellow)
            ) {
                Text("Kaydet", color = TextDark, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))
        } else {
            Button(
                onClick = { showEdit = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryYellow)
            ) {
                Text("Hesap Adı Değiştir", color = TextDark, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Hesabı Sil
        Button(
            onClick = { showDeleteDialog = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Hesabı Sil", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }

    // Silme onayı popup
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Hesabı Sil") },
            text = { Text("Bu hesabı silmek istediğine emin misin?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteAccount()
                        showDeleteDialog = false
                    }
                ) { Text("Evet", color = Color.Red) }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) { Text("Vazgeç") }
            }
        )
    }
}
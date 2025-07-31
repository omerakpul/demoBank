package com.vb.demobankapp.presentation.ui.account.accounts

import android.content.ClipData
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import android.content.Context
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vb.demobankapp.R
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
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

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
        // Header - Geri butonu ve başlık
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
                text = "Hesap Detayları",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Hesap Türü Bölümü
        Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.creditcard),
                    contentDescription = "Account Type",
                    tint = TextDark,
                    modifier = Modifier.size(24.dp)
                )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = currentAccountName,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    fontSize = 16.sp
                )
                Text(
                    text = account.accountType,
                    color = TextPlaceholder,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Hesap Bilgileri Bölümü - Sadece yazılar
        Column {
            // Hesap Numarası
            AccountDetailRow(
                label = "Hesap Numarası",
                value = account.accountNumber
            )

            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

            // IBAN with Copy Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "IBAN",
                        color = TextPlaceholder,
                        fontSize = 14.sp
                    )
                    Text(
                        text = account.iban,
                        color = TextDark,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                IconButton(
                    onClick = {
                        val clip = ClipData.newPlainText("IBAN", account.iban)
                        val clipEntry = ClipEntry(clip)
                        clipboardManager.setClip(clipEntry)
                        Toast.makeText(context, "IBAN kopyalandı", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.contentcopy),
                        contentDescription = "Copy IBAN",
                        tint = TextDark,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

            // Bakiye
            AccountDetailRow(
                label = "Bakiye",
                value = "₺ ${account.balance}"
            )
        }

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
                    currentAccountName = newAccountName
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

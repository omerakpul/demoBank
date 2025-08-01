package com.vb.demobankapp.presentation.ui.account.accounts

import android.content.ClipData
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vb.demobankapp.R
import com.vb.demobankapp.domain.model.AccountInfo
import com.vb.demobankapp.presentation.common.ui.theme.*

@Composable
fun AccountScreen(
    account: AccountInfo,
    onBackClick: () -> Unit,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var newAccountName by remember { mutableStateOf(account.accountName) }
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    // Başarılı işlem sonrası geri dön
    LaunchedEffect(state) {
        if (state is AccountState.Success) {
            onBackClick()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, "Geri", tint = TextDark)
            }
            Spacer(modifier = Modifier.weight(1f))
            Text("Hesap Detayları", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))


        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Hesap Adı", color = TextPlaceholder, fontSize = 14.sp)
                Text(account.accountName, color = TextDark, fontSize = 18.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("IBAN", color = TextPlaceholder, fontSize = 14.sp)
                        Text(account.iban, color = TextDark, fontSize = 16.sp)
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

                Spacer(modifier = Modifier.height(16.dp))

                Text("Bakiye", color = TextPlaceholder, fontSize = 14.sp)
                Text("₺ ${account.balance}", color = TextDark, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { showEditDialog = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryYellow)
        ) {
            Text("Hesap Adı Değiştir", color = TextDark, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { showDeleteDialog = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Hesabı Sil", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }

    // Hesap adı değiştirme popup
    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Hesap Adı Değiştir") },
            text = {
                OutlinedTextField(
                    value = newAccountName,
                    onValueChange = { newAccountName = it },
                    label = { Text("Yeni Hesap Adı") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.updateAccountName(account.accountId, newAccountName)
                        showEditDialog = false
                    }
                ) {
                    Text("Kaydet", color = PrimaryYellow)
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("İptal")
                }
            }
        )
    }

    // Hesap silme uyarısı
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Hesabı Sil") },
            text = { Text("Bu hesabı silmek istediğine emin misin? Bu işlem geri alınamaz.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteAccount(account.accountId)  // Onay verilince sil
                        showDeleteDialog = false
                    }
                ) {
                    Text("Evet, Sil", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Vazgeç")
                }
            }
        )
    }

    // Loading ve Error durumları
    when (state) {
        is AccountState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = PrimaryYellow)
            }
        }
        is AccountState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text((state as AccountState.Error).message, color = Color.Red)
            }
        }
        else -> {}
    }
}
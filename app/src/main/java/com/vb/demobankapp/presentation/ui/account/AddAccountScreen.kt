package com.vb.demobankapp.presentation.ui.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.vb.demobankapp.presentation.common.ui.theme.BackgroundCream
import com.vb.demobankapp.presentation.common.ui.theme.PrimaryYellow
import com.vb.demobankapp.presentation.common.ui.theme.TextDark

@Composable
fun AddAccountScreen(
    onBackClick: () -> Unit,
    onAddClick: (accountName: String) -> Unit,
    viewModel: AddAccountViewModel = hiltViewModel()
) {
    var accountName by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()

    // Success durumunda geri dön
    LaunchedEffect(state) {
        if (state is AddAccountState.Success) {
            onBackClick()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Üstte geri butonu
        Row(
            modifier = Modifier.fillMaxWidth(),
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
        }
        Spacer(modifier = Modifier.height(32.dp))
        // Başlık
        Text(
            text = "Yeni TRY Hesabı Ekle",
            color = TextDark,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(32.dp))
        // Hesap adı inputu
        OutlinedTextField(
            value = accountName,
            onValueChange = { accountName = it },
            label = { Text("Hesap Adı", color = TextDark) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryYellow,
                unfocusedBorderColor = PrimaryYellow.copy(alpha = 0.5f),
                cursorColor = PrimaryYellow,
                focusedLabelColor = PrimaryYellow,
                unfocusedLabelColor = TextDark
            ),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        // Ekle butonu
        Button(
            onClick = {
                viewModel.addAccount(accountName)
            },
            enabled = accountName.isNotBlank() && state !is AddAccountState.Loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryYellow,
                disabledContainerColor = PrimaryYellow.copy(alpha = 0.5f)
            )
        ) {
            if (state is AddAccountState.Loading) {
                CircularProgressIndicator(color = TextDark, modifier = Modifier.size(24.dp))
            } else {
                Text("Hesap Ekle", color = TextDark)
            }
        }

        // Hata mesajı
        if (state is AddAccountState.Error) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = (state as AddAccountState.Error).message,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddAccountScreenPreview() {
    AddAccountScreen(
        onBackClick = {},
        onAddClick = {}
    )
}
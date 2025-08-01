package com.vb.demobankapp.presentation.ui.account.addaccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vb.demobankapp.presentation.common.ui.theme.BackgroundCream
import com.vb.demobankapp.presentation.common.ui.theme.PrimaryYellow
import com.vb.demobankapp.presentation.common.ui.theme.TextDark

@Composable
fun AddAccountScreen(
    onBackClick: () -> Unit,
    viewModel: AddAccountViewModel = hiltViewModel()
) {
    var accountName by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state) {
        if (state is AddAccountState.Success) {
            viewModel.resetState()
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
        Text(
            text = "Yeni TRY Hesabı Ekle",
            color = TextDark,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(32.dp))
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
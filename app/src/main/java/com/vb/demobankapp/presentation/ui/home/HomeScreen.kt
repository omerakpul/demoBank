package com.vb.demobankapp.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import com.vb.demobankapp.R
import com.vb.demobankapp.presentation.common.components.AccountCard
import com.vb.demobankapp.presentation.common.components.AddAccountCard

@Composable
fun HomeScreen(
    onAddAccountClick: () -> Unit,
    onTransferClick: () -> Unit,
    onCurrencyClick: () -> Unit,
    onLogoutClick: () -> Unit, // onLogoutClick parametresini ekledik
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val auth = FirebaseAuth.getInstance()
    val currentUserId = auth.currentUser?.uid ?: ""

    LaunchedEffect(currentUserId) {
        if (currentUserId.isNotEmpty()) {
            viewModel.loadAccounts(currentUserId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Üst kısım - Başlık ve çıkış butonu
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.welcome_back),
                style = MaterialTheme.typography.headlineSmall
            )

            // Çıkış butonu
            IconButton(onClick = onLogoutClick) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Çıkış Yap",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        when (val currentState = state) {
            is HomeState.Loading -> {
                Text(stringResource(R.string.loading))
            }
            is HomeState.Empty -> {
                AddAccountCard(
                    onClick = onAddAccountClick,
                    isFirstCard = true
                )
            }
            is HomeState.Success -> {
                currentState.accounts.forEach { account ->
                    AccountCard(
                        accountName = account.accountName,
                        accountNumber = account.iban,
                        balance = "${account.balance} ${account.accountType}"
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                AddAccountCard(
                    onClick = onAddAccountClick,
                    isFirstCard = false
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = onTransferClick,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(stringResource(R.string.transfer))
                    }

                    Button(
                        onClick = onCurrencyClick,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(stringResource(R.string.currency))
                    }
                }
            }
            is HomeState.Error -> {
                Text(
                    text = currentState.message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
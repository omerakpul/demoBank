package com.vb.demobankapp.presentation.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vb.demobankapp.R
import com.vb.demobankapp.presentation.common.components.AccountCard
import com.vb.demobankapp.presentation.common.components.AddAccountCard

@Composable
fun HomeScreen(
    userId: String,
    onAddAccountClick: () -> Unit,
    onTransferClick: () -> Unit,
    onCurrencyClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(userId) {
        viewModel.loadAccounts(userId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.welcome_back),
            style = MaterialTheme.typography.headlineSmall
        )

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
                        accountName = stringResource(R.string.account_name, account.accountType),
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
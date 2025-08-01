package com.vb.demobankapp.presentation.ui.home

import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import com.vb.demobankapp.R
import com.vb.demobankapp.domain.model.AccountInfo
import com.vb.demobankapp.presentation.common.components.AccountCard
import com.vb.demobankapp.presentation.common.ui.theme.PrimaryYellow
import com.vb.demobankapp.presentation.common.ui.theme.TextDark

@Composable
fun HomeScreen(
    onAddAccountClick: () -> Unit,
    onTransferClick: () -> Unit,
    onCurrencyClick: () -> Unit,
    onAccountClick: (AccountInfo) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val auth = FirebaseAuth.getInstance()
    val currentUserId = FirebaseAuth.getInstance().currentUser?.phoneNumber ?: ""

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
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Hesaplarım",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.Center)
            )

            IconButton(
                onClick = onAddAccountClick,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Hesap Ekle",
                    tint = PrimaryYellow
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        when (val currentState = state) {
            is HomeState.Loading -> {
                Text(stringResource(R.string.loading))
            }

            is HomeState.Empty -> {
                Text("Hesabınız bulunmamaktadır.")
            }

            is HomeState.Success -> {
                currentState.accounts.forEach { account ->
                    AccountCard(
                        accountName = account.accountName,
                        accountNumber = account.iban,
                        balance = "${account.balance} ${account.accountType}",
                        onClick = { onAccountClick(account) }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Transfer Icon
                    IconButton(
                        onClick = onTransferClick,
                        modifier = Modifier
                            .size(56.dp)
                            .border(2.dp, PrimaryYellow, RoundedCornerShape(12.dp))
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.transfer),
                            contentDescription = "Para Transferi",
                            tint = PrimaryYellow,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    IconButton(
                        onClick = onCurrencyClick,
                        modifier = Modifier
                            .size(56.dp)
                            .border(2.dp, PrimaryYellow, RoundedCornerShape(12.dp))
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.currency),
                            contentDescription = "Döviz İşlemleri",
                            tint = PrimaryYellow,
                            modifier = Modifier.size(32.dp)
                        )
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

package com.vb.demobankapp.presentation.ui.home

import com.vb.demobankapp.domain.model.AccountInfo

data class HomeState(
    val isLoading: Boolean = false,
    val accounts: List<AccountInfo> = emptyList(),
    val error: String? = null
)
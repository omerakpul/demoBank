package com.vb.demobankapp.presentation.ui.home

import com.vb.demobankapp.domain.model.AccountInfo

sealed class HomeState {
    data object Loading : HomeState()
    data class Success(val accounts: List<AccountInfo>) : HomeState()
    data class Error(val message: String) : HomeState()
    data object Empty : HomeState()
}
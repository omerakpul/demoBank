package com.vb.demobankapp.data.mapper

import com.vb.demobankapp.data.remote.dto.AccountInfoDto
import com.vb.demobankapp.data.remote.dto.CurrencyConversionDto
import com.vb.demobankapp.data.remote.dto.CurrencyDto
import com.vb.demobankapp.data.remote.dto.UserDto
import com.vb.demobankapp.domain.model.AccountInfo
import com.vb.demobankapp.domain.model.Currency
import com.vb.demobankapp.domain.model.CurrencyConversion
import com.vb.demobankapp.domain.model.User

fun UserDto.toDomain(): User = User(
    name = name ?: "",
    surname = surname ?: "",
    phoneNumber = phoneNumber ?: "",
    birthDate = birthDate ?: "",
    profileImageUrl = profileImageUrl ?: "",
    createdAt = createdAt ?: ""
)

fun User.toDto(): UserDto = UserDto(
    name = name,
    surname = surname,
    phoneNumber = phoneNumber,
    birthDate = birthDate,
    profileImageUrl = profileImageUrl,
    createdAt = createdAt
)

fun AccountInfoDto.toDomain(): AccountInfo = AccountInfo(
    accountId = accountId ?: "",
    accountName = accountName ?: "",
    userId = userId ?: "",
    iban = iban ?: "",
    accountNumber = accountNumber ?: "",
    balance = balance ?: 0.0,
    accountType = accountType ?: "TRY"
)

fun AccountInfo.toDto(): AccountInfoDto = AccountInfoDto(
    accountId = accountId,
    accountName = accountName,
    userId = userId,
    iban = iban,
    accountNumber = accountNumber,
    balance = balance,
    accountType = accountType
)

fun CurrencyDto.toCurrencyList(): List<Currency> {
    val amount = amount ?: 0.0
    val base = base ?: ""
    return rates?.map { (code, rate) ->
        Currency(amount = amount, base = base, code = code, rate = rate)
    } ?: emptyList()
}

fun CurrencyConversionDto.toDomain(): CurrencyConversion = CurrencyConversion(
    amount = amount ?: 0.0,
    from = from ?: "",
    to = to ?: "",
    result = result ?: 0.0
)
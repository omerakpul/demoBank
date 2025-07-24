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
    id = id ?: "",
    name = name ?: "",
    surname = surname ?: "",
    phoneNumber = phoneNumber ?: "",
    birthDate = birthDate ?: ""
)

fun User.toDto(): UserDto = UserDto(
    id = id,
    name = name,
    surname = surname,
    phoneNumber = phoneNumber,
    birthDate = birthDate
)

fun AccountInfoDto.toDomain(): AccountInfo = AccountInfo(
    accountId = accountId ?: "",
    userId = userId ?: "",
    iban = iban ?: "",
    accountNumber = accountNumber ?: "",
    balance = balance ?: 0.0
)

fun AccountInfo.toDto(): AccountInfoDto = AccountInfoDto(
    accountId = accountId,
    userId = userId,
    iban = iban,
    accountNumber = accountNumber,
    balance = balance
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
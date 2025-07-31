package com.vb.demobankapp.di

import com.google.firebase.auth.FirebaseAuth
import com.vb.demobankapp.domain.repository.AccountInfoRepository
import com.vb.demobankapp.domain.repository.CurrencyRepository
import com.vb.demobankapp.domain.repository.UserRepository
import com.vb.demobankapp.domain.usecase.AccountUseCases.AddAccountUseCase
import com.vb.demobankapp.domain.usecase.AddUserUseCase
import com.vb.demobankapp.domain.usecase.ConvertCurrencyUseCase
import com.vb.demobankapp.domain.usecase.AccountUseCases.DeleteAccountUseCase
import com.vb.demobankapp.domain.usecase.DeleteUserUseCase
import com.vb.demobankapp.domain.usecase.AccountUseCases.UpdateAccountUseCase
import com.vb.demobankapp.domain.usecase.AccountUseCases.GetAccountsByUserIdUseCase
import com.vb.demobankapp.domain.usecase.GetAllUsersUseCase
import com.vb.demobankapp.domain.usecase.GetExchangeRatesUseCase
import com.vb.demobankapp.domain.usecase.GetUserByIdUseCase
import com.vb.demobankapp.domain.usecase.LoginUseCase
import com.vb.demobankapp.domain.usecase.ValidateOtpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(
        repository: UserRepository
    ): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideValidateOtpUseCase(
        repository: UserRepository
    ): ValidateOtpUseCase {
        return ValidateOtpUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddUserUseCase(
        repository: UserRepository
    ): AddUserUseCase {
        return AddUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetUserByIdUseCase(
        repository: UserRepository
    ): GetUserByIdUseCase {
        return GetUserByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllUsersUseCase(
        repository: UserRepository
    ): GetAllUsersUseCase {
        return GetAllUsersUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteUserUseCase(
        repository: UserRepository
    ): DeleteUserUseCase {
        return DeleteUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddAccountUseCase(
        repository: AccountInfoRepository,
        auth: FirebaseAuth
    ): AddAccountUseCase {
        return AddAccountUseCase(repository,auth)
    }

    @Provides
    @Singleton
    fun provideUpdateAccountUseCase(
        repository: AccountInfoRepository
    ): UpdateAccountUseCase {
        return UpdateAccountUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAccountsByUserIdUseCase(
        repository: AccountInfoRepository
    ): GetAccountsByUserIdUseCase {
        return GetAccountsByUserIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteAccountUseCase(
        repository: AccountInfoRepository
    ): DeleteAccountUseCase {
        return DeleteAccountUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetExchangeRatesUseCase(
        repository: CurrencyRepository
    ): GetExchangeRatesUseCase {
        return GetExchangeRatesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideConvertCurrencyUseCase(
        repository: CurrencyRepository
    ): ConvertCurrencyUseCase {
        return ConvertCurrencyUseCase(repository)
    }
}
package com.vb.demobankapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import com.vb.demobankapp.BuildConfig
import com.vb.demobankapp.data.remote.api.CurrencyApiService
import com.vb.demobankapp.data.remote.datasource.AccountInfoRemoteDataSource
import com.vb.demobankapp.data.remote.datasource.CurrencyRemoteDataSource
import com.vb.demobankapp.data.remote.datasource.UserRemoteDataSource
import com.vb.demobankapp.data.repository.AccountInfoRepositoryImpl
import com.vb.demobankapp.data.repository.CurrencyRepositoryImpl
import com.vb.demobankapp.data.repository.UserRepositoryImpl
import com.vb.demobankapp.domain.repository.AccountInfoRepository
import com.vb.demobankapp.domain.repository.CurrencyRepository
import com.vb.demobankapp.domain.repository.UserRepository
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCurrencyApiService(retrofit: Retrofit): CurrencyApiService {
        return retrofit.create(CurrencyApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(
        db: FirebaseFirestore,
        auth: FirebaseAuth
    ): UserRemoteDataSource {
        return UserRemoteDataSource(db, auth)
    }

    @Provides
    @Singleton
    fun provideAccountInfoRemoteDataSource(
        db: FirebaseFirestore
    ): AccountInfoRemoteDataSource {
        return AccountInfoRemoteDataSource(db)
    }

    @Provides
    @Singleton
    fun provideCurrencyRemoteDataSource(
        apiService: CurrencyApiService
    ): CurrencyRemoteDataSource {
        return CurrencyRemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        dataSource: UserRemoteDataSource
    ): UserRepository {
        return UserRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideAccountInfoRepository(
        dataSource: AccountInfoRemoteDataSource
    ): AccountInfoRepository {
        return AccountInfoRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(
        dataSource: CurrencyRemoteDataSource
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(dataSource)
    }
}
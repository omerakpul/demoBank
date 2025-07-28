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
import com.vb.demobankapp.data.remote.datasource.UserRemoteDataSource
import com.vb.demobankapp.data.repository.UserRepositoryImpl
import com.vb.demobankapp.domain.repository.UserRepository
import com.vb.demobankapp.domain.usecase.LoginUseCase
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // Retrofit Instance
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Currency API Service
    @Provides
    @Singleton
    fun provideCurrencyApiService(retrofit: Retrofit): CurrencyApiService {
        return retrofit.create(CurrencyApiService::class.java)
    }

    // Firestore
    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    // Authentication
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    // DataSource
    @Provides
    @Singleton
    fun provideUserRemoteDataSource(
        db: FirebaseFirestore,
        auth: FirebaseAuth
    ): UserRemoteDataSource {
        return UserRemoteDataSource(db, auth)
    }

    // Repository
    @Provides
    @Singleton
    fun provideUserRepository(
        dataSource: UserRemoteDataSource
    ): UserRepository {
        return UserRepositoryImpl(dataSource)
    }

    // UseCase
    @Provides
    @Singleton
    fun provideLoginUseCase(
        repository: UserRepository
    ): LoginUseCase {
        return LoginUseCase(repository)
    }
}
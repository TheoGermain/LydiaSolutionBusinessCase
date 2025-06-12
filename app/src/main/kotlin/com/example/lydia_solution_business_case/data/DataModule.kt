package com.example.lydia_solution_business_case.data

import com.example.lydia_solution_business_case.data.datasources.ContactApi
import com.example.lydia_solution_business_case.data.repositories.ContactsRepositoryImpl
import com.example.lydia_solution_business_case.domain.repositories.ContactsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

  @Binds
  @Singleton
  abstract fun bindContactsRepository(impl: ContactsRepositoryImpl): ContactsRepository
}

@Module
@InstallIn(SingletonComponent::class)
object CoroutineDispatchersModule {

  @Provides
  @Named("IO")
  fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  @Singleton
  fun provideRetrofit(): Retrofit = Retrofit.Builder()
    .baseUrl("https://randomuser.me/")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

  @Provides
  @Singleton
  fun provideContactApi(retrofit: Retrofit): ContactApi =
    retrofit.create(ContactApi::class.java)
}
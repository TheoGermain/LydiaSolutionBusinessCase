package com.example.lydia_solution_business_case.data

import com.example.lydia_solution_business_case.data.repositories.ContactsRepositoryImpl
import com.example.lydia_solution_business_case.domain.repositories.ContactsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

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

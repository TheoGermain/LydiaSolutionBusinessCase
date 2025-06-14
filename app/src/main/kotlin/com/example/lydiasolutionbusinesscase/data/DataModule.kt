@file:Suppress("unused")

package com.example.lydiasolutionbusinesscase.data

import android.content.Context
import androidx.room.Room
import com.example.lydiasolutionbusinesscase.data.api.ContactApi
import com.example.lydiasolutionbusinesscase.data.db.ContactDao
import com.example.lydiasolutionbusinesscase.data.db.LocalDatabase
import com.example.lydiasolutionbusinesscase.data.repository.ContactsRepositoryImpl
import com.example.lydiasolutionbusinesscase.domain.repositories.ContactsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
abstract class RepositoryModule {

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

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            LocalDatabase.DATABASE_NAME,
        ).build()
    }

    @Provides
    fun provideItemDao(database: LocalDatabase): ContactDao {
        return database.contactDao()
    }
}

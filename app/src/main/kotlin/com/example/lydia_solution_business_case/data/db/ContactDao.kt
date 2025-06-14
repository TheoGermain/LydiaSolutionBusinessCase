package com.example.lydia_solution_business_case.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts ORDER BY indexInResponse ASC")
    fun pagingSource(): PagingSource<Int, ContactEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ContactEntity>)

    @Query("SELECT MAX(page) FROM contacts")
    suspend fun getMaxPage(): Int?

    @Query("SELECT COUNT(*) FROM contacts")
    suspend fun countItems(): Int

    @Query("DELETE FROM contacts")
    suspend fun clearAll()

    @Query("SELECT * FROM contacts WHERE id = :id LIMIT 1")
    fun getContactById(id: String): Flow<ContactEntity>
}
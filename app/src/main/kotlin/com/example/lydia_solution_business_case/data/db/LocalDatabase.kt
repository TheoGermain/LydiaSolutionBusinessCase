package com.example.lydia_solution_business_case.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ContactEntity::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object {
        const val DATABASE_NAME = "local_database"
    }
}
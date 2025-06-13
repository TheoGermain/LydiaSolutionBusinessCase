package com.example.lydia_solution_business_case.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey val id: String,
    val title: String,
    val firstName: String,
    val lastName: String,
    val pictureUrl: String
)
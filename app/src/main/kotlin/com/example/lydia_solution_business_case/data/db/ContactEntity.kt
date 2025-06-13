package com.example.lydia_solution_business_case.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lydia_solution_business_case.domain.models.Contact

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey val id: String,
    val title: String,
    val firstName: String,
    val lastName: String,
    val pictureUrl: String,
    val page: Int,
    val indexInResponse: Int,
)

fun ContactEntity.toDomain() = Contact(
    id = id,
    title = title,
    firstName = firstName,
    lastName = lastName,
    pictureUrl = pictureUrl
)
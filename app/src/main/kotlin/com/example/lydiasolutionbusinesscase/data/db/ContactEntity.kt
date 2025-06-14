package com.example.lydiasolutionbusinesscase.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lydiasolutionbusinesscase.domain.models.Contact

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey val id: String,
    val title: String,
    val firstName: String,
    val lastName: String,
    val pictureUrl: String,
    val address: String,
    val email: String,
    val phone: String,
    val cell: String,
    val page: Int,
    val indexInResponse: Int,
)

fun ContactEntity.toDomain() = Contact(
    id = id,
    title = title,
    firstName = firstName,
    lastName = lastName,
    pictureUrl = pictureUrl,
    address = address,
    email = email,
    phone = phone,
    cell = cell,
)

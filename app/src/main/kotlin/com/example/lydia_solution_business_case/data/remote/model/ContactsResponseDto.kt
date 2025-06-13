package com.example.lydia_solution_business_case.data.remote.model

import com.example.lydia_solution_business_case.domain.models.Contact
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContactsResponseDto(
    val results: List<ContactDto>,
)

@JsonClass(generateAdapter = true)
data class ContactDto(
    val name: NameDto,
    val login: LoginDto,
    val picture: PictureDto,
)

@JsonClass(generateAdapter = true)
data class NameDto(
    val title: String,
    val first: String,
    val last: String,
)

@JsonClass(generateAdapter = true)
data class LoginDto(
    val uuid: String,
)

@JsonClass(generateAdapter = true)
data class PictureDto(
    val large: String,
)

fun ContactDto.toDomain() = Contact(
    id = login.uuid,
    title = name.title,
    firstName = name.first,
    lastName = name.last,
    pictureUrl = picture.large
)
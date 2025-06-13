package com.example.lydia_solution_business_case.data.remote.model

import com.example.lydia_solution_business_case.data.PAGE_SIZE
import com.example.lydia_solution_business_case.data.db.ContactEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContactsResponseDto(
    val results: List<ContactDto>,
    val info: InfoDto,
)

@JsonClass(generateAdapter = true)
data class InfoDto(
    val page: Int,
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

fun ContactDto.toEntity(page: Int, index: Int) = ContactEntity(
    id = login.uuid,
    page = page,
    title = name.title,
    firstName = name.first,
    lastName = name.last,
    pictureUrl = picture.large,
    indexInResponse = (page - 1) * PAGE_SIZE + index
)
package com.example.lydia_solution_business_case.data.remote.models

import com.example.lydia_solution_business_case.domain.models.Contact
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContactsResponse(
    val results: List<ContactDto>,
)

@JsonClass(generateAdapter = true)
data class ContactDto(
    val name: NameDto,
    val id: IdDto,
    val picture: PictureDto,
)

@JsonClass(generateAdapter = true)
data class NameDto(
    val title: String,
    val first: String,
    val last: String,
)

@JsonClass(generateAdapter = true)
data class IdDto(
    val value: String?,
)

@JsonClass(generateAdapter = true)
data class PictureDto(
    val large: String,
)

fun ContactDto.toDomain() = Contact(
    slug = "${id.value ?: ""}-${name.title}-${name.first}-${name.last}".lowercase(),
    title = name.title,
    firstName = name.first,
    lastName = name.last,
    pictureUrl = picture.large
)
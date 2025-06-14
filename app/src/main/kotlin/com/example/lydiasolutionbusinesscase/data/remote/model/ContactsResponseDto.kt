package com.example.lydiasolutionbusinesscase.data.remote.model

import com.example.lydiasolutionbusinesscase.data.PAGE_SIZE
import com.example.lydiasolutionbusinesscase.data.db.ContactEntity
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
    val location: LocationDto,
    val email: String,
    val login: LoginDto,
    val phone: String,
    val cell: String,
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

@JsonClass(generateAdapter = true)
data class LocationDto(
    val city: String,
    val state: String,
    val country: String,
    val street: StreetDto,
)

@JsonClass(generateAdapter = true)
data class StreetDto(
    val number: Int,
    val name: String,
)

fun ContactDto.toEntity(page: Int, index: Int) = ContactEntity(
    id = login.uuid,
    page = page,
    title = name.title,
    firstName = name.first,
    lastName = name.last,
    pictureUrl = picture.large,
    indexInResponse = (page - 1) * PAGE_SIZE + index,
    address = with(location) { "${street.number} ${street.name}, $city, $state, $country" },
    email = email,
    phone = phone,
    cell = cell,
)

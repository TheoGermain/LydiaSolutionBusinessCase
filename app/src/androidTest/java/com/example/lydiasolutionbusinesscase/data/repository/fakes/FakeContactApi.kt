package com.example.lydiasolutionbusinesscase.data.repository.fakes

import com.example.lydiasolutionbusinesscase.data.api.ContactApi
import com.example.lydiasolutionbusinesscase.data.remote.model.ContactDto
import com.example.lydiasolutionbusinesscase.data.remote.model.ContactsResponseDto
import com.example.lydiasolutionbusinesscase.data.remote.model.InfoDto
import com.example.lydiasolutionbusinesscase.data.remote.model.LocationDto
import com.example.lydiasolutionbusinesscase.data.remote.model.LoginDto
import com.example.lydiasolutionbusinesscase.data.remote.model.NameDto
import com.example.lydiasolutionbusinesscase.data.remote.model.PictureDto
import com.example.lydiasolutionbusinesscase.data.remote.model.StreetDto

class FakeContactApi : ContactApi {
    val fakeContacts = listOf(
        ContactDto(
            name = NameDto(
                title = "Mr.",
                first = "John",
                last = "Doe"
            ),
            location = LocationDto(
                city = "Springfield",
                state = "Illinois",
                country = "USA",
                street = StreetDto(
                    number = 742,
                    name = "Evergreen Terrace"
                )
            ),
            email = "john.doe@email.com",
            login = LoginDto(
                uuid = "123e4567-e89b-12d3-a456-426614174000"
            ),
            phone = "+1234567890",
            cell = "+11231487527",
            picture = PictureDto(
                large = "https://example.com/john.jpg"
            )
        )
    )

    override suspend fun getContacts(
        seed: String,
        results: Int,
        page: Int,
    ): ContactsResponseDto {
        val fakeInfo = InfoDto(
            page = page
        )

        return ContactsResponseDto(results = fakeContacts, info = fakeInfo)
    }
}
package com.example.lydiasolutionbusinesscase.data.repository.fakes

import com.example.lydiasolutionbusinesscase.data.db.ContactEntity
import java.util.UUID

object FakeContactEntityFactory {
    fun create(
        id: String = UUID.randomUUID().toString(),
        page: Int = 1,
        indexInResponse: Int = 0
    ): ContactEntity {
        return ContactEntity(
            id = id,
            firstName = "John",
            lastName = "Doe",
            title = "Mr.",
            email = "john.doe@email.com",
            phone = "+1234567890",
            cell = "+11231487527",
            pictureUrl = "https://example.com/john.jpg",
            address = "742 Evergreen Terrace, Springfield, Illinois, USA",
            page = page,
            indexInResponse = indexInResponse,
        )
    }
}
package com.example.lydia_solution_business_case.data.datasources

import com.example.lydia_solution_business_case.domain.models.Contact

data object MockContactsDataSource {
    fun getMockContacts(): List<Contact> = List(20) {
        Contact(
            udid = it.toString(),
            title = "Mr.",
            firstName = "Contact",
            lastName = it.toString(),
            pictureUrl = "https://randomuser.me/api/portraits/women/27.jpg"
        )
    }
}

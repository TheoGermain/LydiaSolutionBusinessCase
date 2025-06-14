package com.example.lydia_solution_business_case.domain.models

data class Contact(
    val id: String,
    val title: String,
    val firstName: String,
    val lastName: String,
    val pictureUrl: String,
    val address: String,
    val email: String,
    val phone: String,
    val cell: String,
) {
  val fullName: String
    get() = "$title $firstName $lastName"
}

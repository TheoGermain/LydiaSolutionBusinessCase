package com.example.lydia_solution_business_case.data.api

import com.example.lydia_solution_business_case.data.PAGE_SIZE
import com.example.lydia_solution_business_case.data.remote.model.ContactsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ContactApi {
  @GET("api/1.3/")
  suspend fun getContacts(
      @Query("seed") seed: String = "lydia",
      @Query("results") results: Int = PAGE_SIZE,
      @Query("page") page: Int = 1
  ): ContactsResponseDto
}

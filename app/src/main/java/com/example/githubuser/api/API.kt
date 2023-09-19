package com.example.githubuser.api

import com.example.githubuser.data.UserRespons
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface API {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ):Call<UserRespons>
}
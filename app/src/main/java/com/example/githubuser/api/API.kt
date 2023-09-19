package com.example.githubuser.api

import com.example.githubuser.data.DetaiUserResponse
import com.example.githubuser.data.User
import com.example.githubuser.data.UserRespons
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ):Call<UserRespons>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username : String
    ):Call<DetaiUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getfollowers(
        @Path("username") username : String
    ):Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getfollowing(
        @Path("username") username : String
    ):Call<ArrayList<User>>
}
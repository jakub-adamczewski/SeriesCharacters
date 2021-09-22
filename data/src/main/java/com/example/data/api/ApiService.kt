package com.example.data.api

import com.example.data.model.Character
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/api/characters")
    suspend fun searchCharacters(@Query("name") name: String? = null): List<Character>

}
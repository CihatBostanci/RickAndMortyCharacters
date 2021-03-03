package com.example.rickandmortycharacters.api

import com.example.rickandmortycharacters.utils.GET_RICK_AND_MORTY_ALL_CHARACTERS
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {

    @GET(GET_RICK_AND_MORTY_ALL_CHARACTERS)
    suspend fun getAllCharacters(): ResponseBody

    @GET(GET_RICK_AND_MORTY_ALL_CHARACTERS)
    suspend fun getAllCharacters(
        @Query("page") page: Int
    ): ResponseBody

    @GET(GET_RICK_AND_MORTY_ALL_CHARACTERS)
    suspend fun filterByNameAndStatusAllCharacters(
        @Query("name") name: String?,
        @Query("status") status:String?
    ): ResponseBody



}
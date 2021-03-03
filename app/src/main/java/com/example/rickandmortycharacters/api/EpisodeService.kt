package com.example.rickandmortycharacters.api

import com.example.rickandmortycharacters.utils.GET_RICK_AND_MORTY_ALL_CHARACTERS
import com.example.rickandmortycharacters.utils.GET_RICK_AND_MORTY_EPISODES
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface EpisodeService {

    @GET(GET_RICK_AND_MORTY_EPISODES)
    suspend fun getSingleEpisode(
        @Query("id") id: Int
    ): ResponseBody


}
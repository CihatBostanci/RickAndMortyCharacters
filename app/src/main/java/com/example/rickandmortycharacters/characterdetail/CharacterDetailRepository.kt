package com.example.rickandmortycharacters.characterdetail

import com.example.rickandmortycharacters.api.EpisodeService
import com.example.rickandmortycharacters.database.UserDAO
import com.example.rickandmortycharacters.di.safeApiCall
import com.example.rickandmortycharacters.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharacterDetailRepository (private val userDAO: UserDAO,private val episodeService: EpisodeService) {

    suspend fun updateFavoriteCharacter( userId: Int, favoriteCharacterId:Int) {
        withContext(Dispatchers.IO) {
            userDAO.updateFavoriteCharacter(userId,favoriteCharacterId)
        }
    }

    fun getUserByUserId(userId: Int): UserModel {
        return userDAO.getUserById(userId)
    }

    suspend fun getSingleEpisode(episodeId:Int) = safeApiCall {
        episodeService.getSingleEpisode(episodeId)
    }


}
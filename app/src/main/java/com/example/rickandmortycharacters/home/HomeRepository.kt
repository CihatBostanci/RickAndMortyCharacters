package com.example.rickandmortycharacters.home


import com.example.rickandmortycharacters.api.CharacterService
import com.example.rickandmortycharacters.database.UserDAO
import com.example.rickandmortycharacters.di.safeApiCall
import com.example.rickandmortycharacters.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepository ( private val characterService: CharacterService, private val userDAO: UserDAO) {

    suspend fun getAllCharacters() = safeApiCall {
        characterService.getAllCharacters()
    }

    suspend fun getAllCharacters(pageNumber:Int) = safeApiCall {
        characterService.getAllCharacters(pageNumber)
    }

    suspend fun filterAllCharacters(name:String?, status:String?) = safeApiCall {
        characterService.filterByNameAndStatusAllCharacters(name,status)
    }


    suspend fun updateFavoriteCharacter( userId: Int, favoriteCharacterId:Int) {
        withContext(Dispatchers.IO) {
            userDAO.updateFavoriteCharacter(userId,favoriteCharacterId)
        }
    }

    fun getUserByUserId(userId: Int):UserModel {
        return userDAO.getUserById(userId)
    }


}
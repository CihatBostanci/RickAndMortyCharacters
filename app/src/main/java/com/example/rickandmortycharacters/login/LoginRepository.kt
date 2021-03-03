package com.example.rickandmortycharacters.login

import com.example.rickandmortycharacters.database.UserDAO
import com.example.rickandmortycharacters.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository (private val userDAO: UserDAO){

    fun fetchAllUsers(): MutableList<UserModel> {
        return userDAO.findAllUsers()
    }

    suspend fun addUser( user: UserModel) {
        withContext(Dispatchers.IO) {
            userDAO.add(user)
        }
    }

    fun getUserByEmailAndPassword(userEmail: String, userPassword:String):UserModel {
        return userDAO.getUserByEmailAndPassword(userEmail, userPassword)
    }

}
package com.example.rickandmortycharacters.database

import androidx.room.*
import com.example.rickandmortycharacters.model.UserModel

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(users: UserModel)

    @Query("SELECT * FROM UserModel")
    fun findAllUsers(): MutableList<UserModel>

    @Query("SELECT * FROM UserModel WHERE userEmail = :userEmail AND userPassword = :userPassword LIMIT 1")
    fun getUserByEmailAndPassword(userEmail:String, userPassword:String) : UserModel

    @Query("UPDATE UserModel SET favoriteCharacter = :characterId WHERE userId = :userIdParam")
    fun updateFavoriteCharacter( userIdParam:Int, characterId : Int)

    @Query("SELECT * FROM UserModel WHERE userId = :userIdParam")
    fun getUserById(userIdParam:Int) : UserModel

}
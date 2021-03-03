package com.example.rickandmortycharacters.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "UserModel", indices = [Index(value = ["userEmail"], unique = true)])
data class UserModel(
    var userEmail:  String,
    var userPassword: String,
    var userName: String,
    var favoriteCharacter: Int
): Serializable {

    @PrimaryKey(autoGenerate = true)
    var userId: Int = 0

    constructor():this("","","", 0 )

    override fun toString(): String {
        return "UserModel(userEmail='$userEmail', userPassword='$userPassword', userName='$userName', userId=$userId)"
    }


}
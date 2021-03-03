package com.example.rickandmortycharacters.utils

import java.util.regex.Matcher
import java.util.regex.Pattern

//*****************************************************************
//Api Constants
const val baseUrl = "https://rickandmortyapi.com/api/"


//*****************************************************************
//Validation Pattern
val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)

fun isValidPassword(password: String?): Boolean {
    val pattern: Pattern
    val matcher: Matcher
    val passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
    pattern = Pattern.compile(passwordPattern)

    matcher = pattern.matcher(password ?: "")

    return matcher.matches()
}

//*****************************************************************
//Shared User Constants
const val USER_EMAIL = "useremail"
const val USER_PASSWORD = "password"
const val USER_NAME= "username"
const val USER_ID = "userId"
const val FAVORITE_CHARACTER_ID = "FavoriteCharacterId"

//*****************************************************************
//Validation Error Message
const val PASSWORD_INVALID_MESSAGE = "Password is not valid."
const val EMAIL_INVALID_MESSAGE = "Email is not valid."
const val USER_NAME_INVALID_MESSAGE = "User name is not valid. Please give your name length 2-12 characters"

//*****************************************************************
//Generic Message
const val SUCCESS_MESSAGE = "Success"
const val ADDED_SUCCESS = "User Added successfully"
const val DEFAULTERRORMESSAGE = "ERROR OCCURRED"

//*****************************************************************
//API Service Key
const val GET_RICK_AND_MORTY_ALL_CHARACTERS = "character"
const val GET_RICK_AND_MORTY_EPISODES = "episode"


//*****************************************************************
//Bundle Service Key
const val TRANSFER_SELECTED_CHARACTER = "transferSelectedCharacter"

//Recycler View List Type
enum class ListType {
    LIST,
    GRID
}
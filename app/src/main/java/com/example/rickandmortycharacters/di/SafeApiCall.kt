package com.example.rickandmortycharacters.di

import com.example.rickandmortycharacters.utils.DEFAULTERRORMESSAGE
import com.example.rickandmortycharacters.utils.Resource
import retrofit2.HttpException


suspend fun <T : Any> safeApiCall(apiCall: suspend () -> T): Resource<T> {

    return try {
        val response = apiCall()
        Resource.success(response)

    } catch (ex: Exception) {
        when (ex) {
            is HttpException -> {
                when (ex.code()) {
                    400 -> {
                        Resource.error(
                            data = null,
                            message = ex.message ?: DEFAULTERRORMESSAGE
                        )
                    }
                    401 -> {
                        Resource.error(
                            data = null,
                            message = ex.message()
                        )
                    }
                    else ->
                        Resource.error(
                            data = null,
                            message = DEFAULTERRORMESSAGE
                        )
                }
            }
            else -> Resource.error(data = null, message = ex.message ?: DEFAULTERRORMESSAGE)
        }
    }
}

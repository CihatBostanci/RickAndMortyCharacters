package com.example.rickandmortycharacters.model


import com.google.gson.annotations.SerializedName

data class AllCharactersResponseModel(
    @SerializedName("info")
    var info: Info,
    @SerializedName("results")
    var results: MutableList<Result>
){
    constructor():this(Info(0,"",0,""), mutableListOf())
}
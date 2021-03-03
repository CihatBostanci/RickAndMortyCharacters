package com.example.rickandmortycharacters.model


import com.google.gson.annotations.SerializedName

data class Origin(
    @SerializedName("name")
    var name: String,
    @SerializedName("url")
    var url: String
)
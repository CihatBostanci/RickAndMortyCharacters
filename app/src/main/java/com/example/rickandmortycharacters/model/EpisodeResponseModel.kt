package com.example.rickandmortycharacters.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EpisodeResponseModel(
    @SerializedName("info")
    var info: Info,
    @SerializedName("results")
    var results: MutableList<ResultX>
):Serializable {
    constructor():this(Info(0,"",0,""), mutableListOf())
}
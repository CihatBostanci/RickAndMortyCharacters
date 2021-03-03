package com.example.rickandmortycharacters.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResultX(
    @SerializedName("air_date")
    var airDate: String,
    @SerializedName("characters")
    var characters: MutableList<String>,
    @SerializedName("created")
    var created: String,
    @SerializedName("episode")
    var episode: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("url")
    var url: String
) :Serializable {
    constructor():this("", mutableListOf(),"","",0,"","")
}
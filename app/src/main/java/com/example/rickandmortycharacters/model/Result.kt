package com.example.rickandmortycharacters.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Result(
    @SerializedName("created")
    var created: String,
    @SerializedName("episode")
    var episode: MutableList<String>,
    @SerializedName("gender")
    var gender: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("image")
    var image: String,
    @SerializedName("location")
    var location: Location,
    @SerializedName("name")
    var name: String,
    @SerializedName("origin")
    var origin: Origin,
    @SerializedName("species")
    var species: String,
    @SerializedName("status")
    var status: String,
    @SerializedName("type")
    var type: String,
    @SerializedName("url")
    var url: String
) :Serializable {
    override fun toString(): String {
        return "Result(created='$created', episode=$episode, gender='$gender', id=$id, image='$image', location=$location, name='$name', origin=$origin, species='$species', status='$status', type='$type', url='$url')"
    }
}
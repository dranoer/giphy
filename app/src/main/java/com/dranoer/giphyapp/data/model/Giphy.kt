package com.dranoer.giphyapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Giphy(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("title")
    val title: String?,

    @field:SerializedName("images")
    val images: GiphyImage,

    @field:SerializedName("user")
    val user: User?,
)

@Entity(tableName = "giphy_table")
data class GiphyEntity(
    @PrimaryKey
    val id: String,

    val title: String = "",
    val isFavorite: Boolean = false,

//    val images: GiphyImage,
//    val user: User?,
)
package com.dranoer.giphyapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object, Model for remote
 */
data class GiphyDTO(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("title")
    val title: String?,

    @field:SerializedName("images")
    val images: GiphyImage,

    @field:SerializedName("user")
    val user: User?,
)

/**
 * Model for dataBase
 */
@Entity(tableName = "giphy_table")
data class GiphyEntity(
    @PrimaryKey
    val id: String,
    val title: String = "",
    val previewUrl: String,
    val username: String?,
    val isFavorite: Boolean = false,
)

/**
 * Model for entity, a unique model for ui to interact with
 */
data class Giphy(
    val id: String,
    val title: String?,
    val previewUrl: String,
    val username: String?,
    val isFavorite: Boolean = false,
)
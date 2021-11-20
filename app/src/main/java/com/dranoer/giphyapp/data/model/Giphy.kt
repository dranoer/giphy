package com.dranoer.giphyapp.data.model

import com.google.gson.annotations.SerializedName

data class Giphy(
    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("images")
    val images: GiphyImage,
)

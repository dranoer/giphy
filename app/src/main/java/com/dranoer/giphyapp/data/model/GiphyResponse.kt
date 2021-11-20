package com.dranoer.giphyapp.data.model

import com.google.gson.annotations.SerializedName

data class GiphyResponse(
    @field:SerializedName("data")
    val giphies: List<Giphy>,
)
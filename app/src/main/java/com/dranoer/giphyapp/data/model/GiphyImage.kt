package com.dranoer.giphyapp.data.model

import com.google.gson.annotations.SerializedName

data class GiphyImage(
    @field:SerializedName("preview_gif")
    val preview: Preview?,
)
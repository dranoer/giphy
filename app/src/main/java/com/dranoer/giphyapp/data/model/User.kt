package com.dranoer.giphyapp.data.model

import com.google.gson.annotations.SerializedName

data class User(

    @field:SerializedName("display_name")
    val name: String?,
)
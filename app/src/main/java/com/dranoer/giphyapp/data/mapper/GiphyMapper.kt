package com.dranoer.giphyapp.data.mapper

import com.dranoer.giphyapp.data.model.Giphy
import com.dranoer.giphyapp.data.model.GiphyDTO

fun GiphyDTO.mapToEntity(): Giphy {
    return Giphy(
        id = this.id,
        title = this.title!!,
        isFavorite = false,
        previewUrl = this.images.preview.imageUrl,
        username = this.user?.name
    )
}
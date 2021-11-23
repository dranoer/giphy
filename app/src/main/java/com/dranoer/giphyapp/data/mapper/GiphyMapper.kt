package com.dranoer.giphyapp.data.mapper

import com.dranoer.giphyapp.data.model.Giphy
import com.dranoer.giphyapp.data.model.GiphyDTO
import com.dranoer.giphyapp.data.model.GiphyEntity

fun GiphyDTO.mapToDomain(): Giphy {
    return Giphy(
        id = this.id,
        title = this.title!!,
        isFavorite = false,
        previewUrl = this.images.preview.imageUrl,
        username = this.user?.name,
    )
}

fun GiphyEntity.mapToDomain(): Giphy {
    return Giphy(
        id = this.id,
        title = this.title,
        isFavorite = this.isFavorite,
        previewUrl = this.previewUrl,
        username = this.username,
    )
}

fun Giphy.mapToEntity(): GiphyEntity {
    return GiphyEntity(
        id = this.id,
        title = this.title ?: "",
        isFavorite = this.isFavorite,
        previewUrl = this.previewUrl,
        username = this.username,
    )
}
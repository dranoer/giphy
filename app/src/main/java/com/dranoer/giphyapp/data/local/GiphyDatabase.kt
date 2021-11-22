package com.dranoer.giphyapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dranoer.giphyapp.data.model.GiphyEntity

@Database(entities = [GiphyEntity::class], version = 1, exportSchema = false)
abstract class GiphyDatabase : RoomDatabase() {
    abstract fun giphyDao(): GiphyDao
}
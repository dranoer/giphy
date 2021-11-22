package com.dranoer.giphyapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dranoer.giphyapp.data.model.GiphyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GiphyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGiphies(giphies: List<GiphyEntity>)

    @Query("SELECT * FROM giphy_table")
    fun getGiphies(): Flow<List<GiphyEntity>>
}
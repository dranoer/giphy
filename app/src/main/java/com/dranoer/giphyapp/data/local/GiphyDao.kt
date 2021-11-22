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

    @Query("SELECT isFavorite FROM giphy_table WHERE id = :id")
    suspend fun getGiphy(id: String): Boolean

    /**
     * Updating only isFavorite entity
     * By giphy id
     */
    @Query("UPDATE giphy_table SET isFavorite = :isFavorite WHERE id =:id")
    suspend fun updateGiphy(id: String, isFavorite: Boolean)

    @Query("SELECT * FROM giphy_table WHERE isFavorite =:isFavorite")
    fun getFavorites(isFavorite: Boolean): Flow<List<GiphyEntity>>
}
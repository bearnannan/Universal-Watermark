package com.universalwatermark.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.universalwatermark.data.local.db.entity.WatermarkHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WatermarkHistoryDao {
    @Query("SELECT * FROM watermark_history ORDER BY processedAt DESC")
    fun getAllHistory(): Flow<List<WatermarkHistoryEntity>>

    @Query("SELECT * FROM watermark_history WHERE originalUri = :uri LIMIT 1")
    suspend fun getHistoryByUri(uri: String): WatermarkHistoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: WatermarkHistoryEntity): Long
    
    @Query("DELETE FROM watermark_history WHERE id = :id")
    suspend fun deleteHistory(id: Long)
}

package com.universalwatermark.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.universalwatermark.data.local.db.entity.WatermarkTemplateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WatermarkTemplateDao {
    @Query("SELECT * FROM watermark_templates ORDER BY updatedAt DESC")
    fun getAllTemplates(): Flow<List<WatermarkTemplateEntity>>

    @Query("SELECT * FROM watermark_templates WHERE isActive = 1 LIMIT 1")
    fun getActiveTemplate(): Flow<WatermarkTemplateEntity?>
    
    @Query("SELECT * FROM watermark_templates WHERE isActive = 1 LIMIT 1")
    suspend fun getActiveTemplateSync(): WatermarkTemplateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplate(template: WatermarkTemplateEntity): Long

    @Update
    suspend fun updateTemplate(template: WatermarkTemplateEntity)

    @Query("UPDATE watermark_templates SET isActive = 0")
    suspend fun deactivateAllTemplates()

    @Query("UPDATE watermark_templates SET isActive = 1 WHERE id = :id")
    suspend fun setActiveTemplate(id: Long)
    
    @Query("DELETE FROM watermark_templates WHERE id = :id")
    suspend fun deleteTemplate(id: Long)
}

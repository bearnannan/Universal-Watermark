package com.universalwatermark.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watermark_templates")
data class WatermarkTemplateEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val templateText: String,    
    val isActive: Boolean,
    val styleJson: String,       
    val positionJson: String,    
    val createdAt: Long,
    val updatedAt: Long
)

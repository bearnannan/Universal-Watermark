package com.universalwatermark.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watermark_history")
data class WatermarkHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val originalUri: String,
    val watermarkedUri: String,
    val templateId: Long,
    val processedAt: Long,
    val metadataJson: String, // JSON serialized metadata
    val status: String        // SUCCESS, FAILED, SKIPPED
)

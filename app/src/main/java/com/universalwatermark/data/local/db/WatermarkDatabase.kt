package com.universalwatermark.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.universalwatermark.data.local.db.dao.WatermarkHistoryDao
import com.universalwatermark.data.local.db.dao.WatermarkTemplateDao
import com.universalwatermark.data.local.db.entity.WatermarkHistoryEntity
import com.universalwatermark.data.local.db.entity.WatermarkTemplateEntity

@Database(
    entities = [WatermarkHistoryEntity::class, WatermarkTemplateEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WatermarkDatabase : RoomDatabase() {
    abstract fun watermarkHistoryDao(): WatermarkHistoryDao
    abstract fun watermarkTemplateDao(): WatermarkTemplateDao
}

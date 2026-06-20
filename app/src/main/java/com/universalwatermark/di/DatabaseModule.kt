package com.universalwatermark.di

import android.content.Context
import androidx.room.Room
import com.universalwatermark.data.local.db.WatermarkDatabase
import com.universalwatermark.data.local.db.dao.WatermarkHistoryDao
import com.universalwatermark.data.local.db.dao.WatermarkTemplateDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideWatermarkDatabase(@ApplicationContext context: Context): WatermarkDatabase {
        return Room.databaseBuilder(
            context,
            WatermarkDatabase::class.java,
            "watermark_db"
        ).build()
    }

    @Provides
    fun provideHistoryDao(database: WatermarkDatabase): WatermarkHistoryDao {
        return database.watermarkHistoryDao()
    }

    @Provides
    fun provideTemplateDao(database: WatermarkDatabase): WatermarkTemplateDao {
        return database.watermarkTemplateDao()
    }
}

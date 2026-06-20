package com.universalwatermark.data.local.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.universalwatermark.data.local.db.dao.WatermarkHistoryDao;
import com.universalwatermark.data.local.db.dao.WatermarkHistoryDao_Impl;
import com.universalwatermark.data.local.db.dao.WatermarkTemplateDao;
import com.universalwatermark.data.local.db.dao.WatermarkTemplateDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WatermarkDatabase_Impl extends WatermarkDatabase {
  private volatile WatermarkHistoryDao _watermarkHistoryDao;

  private volatile WatermarkTemplateDao _watermarkTemplateDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `watermark_history` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `originalUri` TEXT NOT NULL, `watermarkedUri` TEXT NOT NULL, `templateId` INTEGER NOT NULL, `processedAt` INTEGER NOT NULL, `metadataJson` TEXT NOT NULL, `status` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `watermark_templates` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `templateText` TEXT NOT NULL, `isActive` INTEGER NOT NULL, `styleJson` TEXT NOT NULL, `positionJson` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'ed8d7660e036bd45f5eae658200138ce')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `watermark_history`");
        db.execSQL("DROP TABLE IF EXISTS `watermark_templates`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsWatermarkHistory = new HashMap<String, TableInfo.Column>(7);
        _columnsWatermarkHistory.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWatermarkHistory.put("originalUri", new TableInfo.Column("originalUri", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWatermarkHistory.put("watermarkedUri", new TableInfo.Column("watermarkedUri", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWatermarkHistory.put("templateId", new TableInfo.Column("templateId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWatermarkHistory.put("processedAt", new TableInfo.Column("processedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWatermarkHistory.put("metadataJson", new TableInfo.Column("metadataJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWatermarkHistory.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWatermarkHistory = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWatermarkHistory = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWatermarkHistory = new TableInfo("watermark_history", _columnsWatermarkHistory, _foreignKeysWatermarkHistory, _indicesWatermarkHistory);
        final TableInfo _existingWatermarkHistory = TableInfo.read(db, "watermark_history");
        if (!_infoWatermarkHistory.equals(_existingWatermarkHistory)) {
          return new RoomOpenHelper.ValidationResult(false, "watermark_history(com.universalwatermark.data.local.db.entity.WatermarkHistoryEntity).\n"
                  + " Expected:\n" + _infoWatermarkHistory + "\n"
                  + " Found:\n" + _existingWatermarkHistory);
        }
        final HashMap<String, TableInfo.Column> _columnsWatermarkTemplates = new HashMap<String, TableInfo.Column>(8);
        _columnsWatermarkTemplates.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWatermarkTemplates.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWatermarkTemplates.put("templateText", new TableInfo.Column("templateText", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWatermarkTemplates.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWatermarkTemplates.put("styleJson", new TableInfo.Column("styleJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWatermarkTemplates.put("positionJson", new TableInfo.Column("positionJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWatermarkTemplates.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWatermarkTemplates.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWatermarkTemplates = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWatermarkTemplates = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWatermarkTemplates = new TableInfo("watermark_templates", _columnsWatermarkTemplates, _foreignKeysWatermarkTemplates, _indicesWatermarkTemplates);
        final TableInfo _existingWatermarkTemplates = TableInfo.read(db, "watermark_templates");
        if (!_infoWatermarkTemplates.equals(_existingWatermarkTemplates)) {
          return new RoomOpenHelper.ValidationResult(false, "watermark_templates(com.universalwatermark.data.local.db.entity.WatermarkTemplateEntity).\n"
                  + " Expected:\n" + _infoWatermarkTemplates + "\n"
                  + " Found:\n" + _existingWatermarkTemplates);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "ed8d7660e036bd45f5eae658200138ce", "f6d7fbdd362cf0729fda0625028916a1");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "watermark_history","watermark_templates");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `watermark_history`");
      _db.execSQL("DELETE FROM `watermark_templates`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(WatermarkHistoryDao.class, WatermarkHistoryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(WatermarkTemplateDao.class, WatermarkTemplateDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public WatermarkHistoryDao watermarkHistoryDao() {
    if (_watermarkHistoryDao != null) {
      return _watermarkHistoryDao;
    } else {
      synchronized(this) {
        if(_watermarkHistoryDao == null) {
          _watermarkHistoryDao = new WatermarkHistoryDao_Impl(this);
        }
        return _watermarkHistoryDao;
      }
    }
  }

  @Override
  public WatermarkTemplateDao watermarkTemplateDao() {
    if (_watermarkTemplateDao != null) {
      return _watermarkTemplateDao;
    } else {
      synchronized(this) {
        if(_watermarkTemplateDao == null) {
          _watermarkTemplateDao = new WatermarkTemplateDao_Impl(this);
        }
        return _watermarkTemplateDao;
      }
    }
  }
}

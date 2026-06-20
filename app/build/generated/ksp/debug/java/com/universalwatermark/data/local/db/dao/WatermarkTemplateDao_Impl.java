package com.universalwatermark.data.local.db.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.universalwatermark.data.local.db.entity.WatermarkTemplateEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WatermarkTemplateDao_Impl implements WatermarkTemplateDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WatermarkTemplateEntity> __insertionAdapterOfWatermarkTemplateEntity;

  private final EntityDeletionOrUpdateAdapter<WatermarkTemplateEntity> __updateAdapterOfWatermarkTemplateEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeactivateAllTemplates;

  private final SharedSQLiteStatement __preparedStmtOfSetActiveTemplate;

  private final SharedSQLiteStatement __preparedStmtOfDeleteTemplate;

  public WatermarkTemplateDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWatermarkTemplateEntity = new EntityInsertionAdapter<WatermarkTemplateEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `watermark_templates` (`id`,`name`,`templateText`,`isActive`,`styleJson`,`positionJson`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WatermarkTemplateEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getTemplateText());
        final int _tmp = entity.isActive() ? 1 : 0;
        statement.bindLong(4, _tmp);
        statement.bindString(5, entity.getStyleJson());
        statement.bindString(6, entity.getPositionJson());
        statement.bindLong(7, entity.getCreatedAt());
        statement.bindLong(8, entity.getUpdatedAt());
      }
    };
    this.__updateAdapterOfWatermarkTemplateEntity = new EntityDeletionOrUpdateAdapter<WatermarkTemplateEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `watermark_templates` SET `id` = ?,`name` = ?,`templateText` = ?,`isActive` = ?,`styleJson` = ?,`positionJson` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WatermarkTemplateEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getTemplateText());
        final int _tmp = entity.isActive() ? 1 : 0;
        statement.bindLong(4, _tmp);
        statement.bindString(5, entity.getStyleJson());
        statement.bindString(6, entity.getPositionJson());
        statement.bindLong(7, entity.getCreatedAt());
        statement.bindLong(8, entity.getUpdatedAt());
        statement.bindLong(9, entity.getId());
      }
    };
    this.__preparedStmtOfDeactivateAllTemplates = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE watermark_templates SET isActive = 0";
        return _query;
      }
    };
    this.__preparedStmtOfSetActiveTemplate = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE watermark_templates SET isActive = 1 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteTemplate = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM watermark_templates WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertTemplate(final WatermarkTemplateEntity template,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfWatermarkTemplateEntity.insertAndReturnId(template);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTemplate(final WatermarkTemplateEntity template,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfWatermarkTemplateEntity.handle(template);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deactivateAllTemplates(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeactivateAllTemplates.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeactivateAllTemplates.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object setActiveTemplate(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetActiveTemplate.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfSetActiveTemplate.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTemplate(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteTemplate.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteTemplate.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<WatermarkTemplateEntity>> getAllTemplates() {
    final String _sql = "SELECT * FROM watermark_templates ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"watermark_templates"}, new Callable<List<WatermarkTemplateEntity>>() {
      @Override
      @NonNull
      public List<WatermarkTemplateEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfTemplateText = CursorUtil.getColumnIndexOrThrow(_cursor, "templateText");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfStyleJson = CursorUtil.getColumnIndexOrThrow(_cursor, "styleJson");
          final int _cursorIndexOfPositionJson = CursorUtil.getColumnIndexOrThrow(_cursor, "positionJson");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<WatermarkTemplateEntity> _result = new ArrayList<WatermarkTemplateEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WatermarkTemplateEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpTemplateText;
            _tmpTemplateText = _cursor.getString(_cursorIndexOfTemplateText);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final String _tmpStyleJson;
            _tmpStyleJson = _cursor.getString(_cursorIndexOfStyleJson);
            final String _tmpPositionJson;
            _tmpPositionJson = _cursor.getString(_cursorIndexOfPositionJson);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new WatermarkTemplateEntity(_tmpId,_tmpName,_tmpTemplateText,_tmpIsActive,_tmpStyleJson,_tmpPositionJson,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<WatermarkTemplateEntity> getActiveTemplate() {
    final String _sql = "SELECT * FROM watermark_templates WHERE isActive = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"watermark_templates"}, new Callable<WatermarkTemplateEntity>() {
      @Override
      @Nullable
      public WatermarkTemplateEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfTemplateText = CursorUtil.getColumnIndexOrThrow(_cursor, "templateText");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfStyleJson = CursorUtil.getColumnIndexOrThrow(_cursor, "styleJson");
          final int _cursorIndexOfPositionJson = CursorUtil.getColumnIndexOrThrow(_cursor, "positionJson");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final WatermarkTemplateEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpTemplateText;
            _tmpTemplateText = _cursor.getString(_cursorIndexOfTemplateText);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final String _tmpStyleJson;
            _tmpStyleJson = _cursor.getString(_cursorIndexOfStyleJson);
            final String _tmpPositionJson;
            _tmpPositionJson = _cursor.getString(_cursorIndexOfPositionJson);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new WatermarkTemplateEntity(_tmpId,_tmpName,_tmpTemplateText,_tmpIsActive,_tmpStyleJson,_tmpPositionJson,_tmpCreatedAt,_tmpUpdatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getActiveTemplateSync(
      final Continuation<? super WatermarkTemplateEntity> $completion) {
    final String _sql = "SELECT * FROM watermark_templates WHERE isActive = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<WatermarkTemplateEntity>() {
      @Override
      @Nullable
      public WatermarkTemplateEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfTemplateText = CursorUtil.getColumnIndexOrThrow(_cursor, "templateText");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfStyleJson = CursorUtil.getColumnIndexOrThrow(_cursor, "styleJson");
          final int _cursorIndexOfPositionJson = CursorUtil.getColumnIndexOrThrow(_cursor, "positionJson");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final WatermarkTemplateEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpTemplateText;
            _tmpTemplateText = _cursor.getString(_cursorIndexOfTemplateText);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final String _tmpStyleJson;
            _tmpStyleJson = _cursor.getString(_cursorIndexOfStyleJson);
            final String _tmpPositionJson;
            _tmpPositionJson = _cursor.getString(_cursorIndexOfPositionJson);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new WatermarkTemplateEntity(_tmpId,_tmpName,_tmpTemplateText,_tmpIsActive,_tmpStyleJson,_tmpPositionJson,_tmpCreatedAt,_tmpUpdatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

package com.universalwatermark.data.local.db.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.universalwatermark.data.local.db.entity.WatermarkHistoryEntity;
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
public final class WatermarkHistoryDao_Impl implements WatermarkHistoryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WatermarkHistoryEntity> __insertionAdapterOfWatermarkHistoryEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteHistory;

  public WatermarkHistoryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWatermarkHistoryEntity = new EntityInsertionAdapter<WatermarkHistoryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `watermark_history` (`id`,`originalUri`,`watermarkedUri`,`templateId`,`processedAt`,`metadataJson`,`status`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WatermarkHistoryEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getOriginalUri());
        statement.bindString(3, entity.getWatermarkedUri());
        statement.bindLong(4, entity.getTemplateId());
        statement.bindLong(5, entity.getProcessedAt());
        statement.bindString(6, entity.getMetadataJson());
        statement.bindString(7, entity.getStatus());
      }
    };
    this.__preparedStmtOfDeleteHistory = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM watermark_history WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertHistory(final WatermarkHistoryEntity history,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfWatermarkHistoryEntity.insertAndReturnId(history);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteHistory(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteHistory.acquire();
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
          __preparedStmtOfDeleteHistory.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<WatermarkHistoryEntity>> getAllHistory() {
    final String _sql = "SELECT * FROM watermark_history ORDER BY processedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"watermark_history"}, new Callable<List<WatermarkHistoryEntity>>() {
      @Override
      @NonNull
      public List<WatermarkHistoryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfOriginalUri = CursorUtil.getColumnIndexOrThrow(_cursor, "originalUri");
          final int _cursorIndexOfWatermarkedUri = CursorUtil.getColumnIndexOrThrow(_cursor, "watermarkedUri");
          final int _cursorIndexOfTemplateId = CursorUtil.getColumnIndexOrThrow(_cursor, "templateId");
          final int _cursorIndexOfProcessedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "processedAt");
          final int _cursorIndexOfMetadataJson = CursorUtil.getColumnIndexOrThrow(_cursor, "metadataJson");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<WatermarkHistoryEntity> _result = new ArrayList<WatermarkHistoryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WatermarkHistoryEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpOriginalUri;
            _tmpOriginalUri = _cursor.getString(_cursorIndexOfOriginalUri);
            final String _tmpWatermarkedUri;
            _tmpWatermarkedUri = _cursor.getString(_cursorIndexOfWatermarkedUri);
            final long _tmpTemplateId;
            _tmpTemplateId = _cursor.getLong(_cursorIndexOfTemplateId);
            final long _tmpProcessedAt;
            _tmpProcessedAt = _cursor.getLong(_cursorIndexOfProcessedAt);
            final String _tmpMetadataJson;
            _tmpMetadataJson = _cursor.getString(_cursorIndexOfMetadataJson);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            _item = new WatermarkHistoryEntity(_tmpId,_tmpOriginalUri,_tmpWatermarkedUri,_tmpTemplateId,_tmpProcessedAt,_tmpMetadataJson,_tmpStatus);
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
  public Object getHistoryByUri(final String uri,
      final Continuation<? super WatermarkHistoryEntity> $completion) {
    final String _sql = "SELECT * FROM watermark_history WHERE originalUri = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, uri);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<WatermarkHistoryEntity>() {
      @Override
      @Nullable
      public WatermarkHistoryEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfOriginalUri = CursorUtil.getColumnIndexOrThrow(_cursor, "originalUri");
          final int _cursorIndexOfWatermarkedUri = CursorUtil.getColumnIndexOrThrow(_cursor, "watermarkedUri");
          final int _cursorIndexOfTemplateId = CursorUtil.getColumnIndexOrThrow(_cursor, "templateId");
          final int _cursorIndexOfProcessedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "processedAt");
          final int _cursorIndexOfMetadataJson = CursorUtil.getColumnIndexOrThrow(_cursor, "metadataJson");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final WatermarkHistoryEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpOriginalUri;
            _tmpOriginalUri = _cursor.getString(_cursorIndexOfOriginalUri);
            final String _tmpWatermarkedUri;
            _tmpWatermarkedUri = _cursor.getString(_cursorIndexOfWatermarkedUri);
            final long _tmpTemplateId;
            _tmpTemplateId = _cursor.getLong(_cursorIndexOfTemplateId);
            final long _tmpProcessedAt;
            _tmpProcessedAt = _cursor.getLong(_cursorIndexOfProcessedAt);
            final String _tmpMetadataJson;
            _tmpMetadataJson = _cursor.getString(_cursorIndexOfMetadataJson);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            _result = new WatermarkHistoryEntity(_tmpId,_tmpOriginalUri,_tmpWatermarkedUri,_tmpTemplateId,_tmpProcessedAt,_tmpMetadataJson,_tmpStatus);
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

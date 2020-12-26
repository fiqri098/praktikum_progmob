package com.example.praktikum.Database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.example.praktikum.Dao.PendaftaranDao;
import com.example.praktikum.Dao.PendaftaranDao_Impl;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public final class RoomDB_Impl extends RoomDB {
  private volatile PendaftaranDao _pendaftaranDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `pendaftarans` (`id` TEXT NOT NULL, `id_user` TEXT, `poli` TEXT, `keluhan` TEXT, `tgl_regis` TEXT, `penyakit_bawaan` TEXT, `tinggi_badan` TEXT, `status` TEXT, `berat_badan` TEXT, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"5df0dd70f35441844ee7b72450ef1545\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `pendaftarans`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsPendaftarans = new HashMap<String, TableInfo.Column>(9);
        _columnsPendaftarans.put("id", new TableInfo.Column("id", "TEXT", true, 1));
        _columnsPendaftarans.put("id_user", new TableInfo.Column("id_user", "TEXT", false, 0));
        _columnsPendaftarans.put("poli", new TableInfo.Column("poli", "TEXT", false, 0));
        _columnsPendaftarans.put("keluhan", new TableInfo.Column("keluhan", "TEXT", false, 0));
        _columnsPendaftarans.put("tgl_regis", new TableInfo.Column("tgl_regis", "TEXT", false, 0));
        _columnsPendaftarans.put("penyakit_bawaan", new TableInfo.Column("penyakit_bawaan", "TEXT", false, 0));
        _columnsPendaftarans.put("tinggi_badan", new TableInfo.Column("tinggi_badan", "TEXT", false, 0));
        _columnsPendaftarans.put("status", new TableInfo.Column("status", "TEXT", false, 0));
        _columnsPendaftarans.put("berat_badan", new TableInfo.Column("berat_badan", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPendaftarans = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPendaftarans = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPendaftarans = new TableInfo("pendaftarans", _columnsPendaftarans, _foreignKeysPendaftarans, _indicesPendaftarans);
        final TableInfo _existingPendaftarans = TableInfo.read(_db, "pendaftarans");
        if (! _infoPendaftarans.equals(_existingPendaftarans)) {
          throw new IllegalStateException("Migration didn't properly handle pendaftarans(com.example.praktikum.Model.Pendaftaran).\n"
                  + " Expected:\n" + _infoPendaftarans + "\n"
                  + " Found:\n" + _existingPendaftarans);
        }
      }
    }, "5df0dd70f35441844ee7b72450ef1545", "63a12e992489fbc5a9b1fae7693d9837");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "pendaftarans");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `pendaftarans`");
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
  public PendaftaranDao pendaftaranDao() {
    if (_pendaftaranDao != null) {
      return _pendaftaranDao;
    } else {
      synchronized(this) {
        if(_pendaftaranDao == null) {
          _pendaftaranDao = new PendaftaranDao_Impl(this);
        }
        return _pendaftaranDao;
      }
    }
  }
}

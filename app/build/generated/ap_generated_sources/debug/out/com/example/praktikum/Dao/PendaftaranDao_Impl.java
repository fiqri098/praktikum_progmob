package com.example.praktikum.Dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.praktikum.Model.Pendaftaran;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class PendaftaranDao_Impl implements PendaftaranDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfPendaftaran;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfDeletePendaftaran;

  private final SharedSQLiteStatement __preparedStmtOfAcceptRegistrasi;

  private final SharedSQLiteStatement __preparedStmtOfRefuseRegistrasi;

  public PendaftaranDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPendaftaran = new EntityInsertionAdapter<Pendaftaran>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `pendaftarans`(`id`,`id_user`,`poli`,`keluhan`,`tgl_regis`,`penyakit_bawaan`,`tinggi_badan`,`status`,`berat_badan`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Pendaftaran value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getId_user() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getId_user());
        }
        if (value.getPoli() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getPoli());
        }
        if (value.getKeluhan() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getKeluhan());
        }
        if (value.getTgl_regis() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getTgl_regis());
        }
        if (value.getPenyakit_bawaan() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getPenyakit_bawaan());
        }
        if (value.getTinggi_badan() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getTinggi_badan());
        }
        if (value.getStatus() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getStatus());
        }
        if (value.getBerat_badan() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getBerat_badan());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM pendaftarans";
        return _query;
      }
    };
    this.__preparedStmtOfDeletePendaftaran = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM pendaftarans where ID = ?";
        return _query;
      }
    };
    this.__preparedStmtOfAcceptRegistrasi = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE pendaftarans set tgl_regis = ?, status = ? where ID = ?";
        return _query;
      }
    };
    this.__preparedStmtOfRefuseRegistrasi = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE pendaftarans set  status = ? where ID = ?";
        return _query;
      }
    };
  }

  @Override
  public void insertPendaftaran(Pendaftaran pendaftaran) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfPendaftaran.insert(pendaftaran);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public void deletePendaftaran(int id) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePendaftaran.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      _stmt.bindLong(_argIndex, id);
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeletePendaftaran.release(_stmt);
    }
  }

  @Override
  public void acceptRegistrasi(String tglRegis, String stat, int sID) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfAcceptRegistrasi.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (tglRegis == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, tglRegis);
      }
      _argIndex = 2;
      if (stat == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, stat);
      }
      _argIndex = 3;
      _stmt.bindLong(_argIndex, sID);
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfAcceptRegistrasi.release(_stmt);
    }
  }

  @Override
  public void refuseRegistrasi(String stat, int sID) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfRefuseRegistrasi.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (stat == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, stat);
      }
      _argIndex = 2;
      _stmt.bindLong(_argIndex, sID);
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfRefuseRegistrasi.release(_stmt);
    }
  }

  @Override
  public List<Pendaftaran> loadAllPendaftaranss() {
    final String _sql = "SELECT * FROM pendaftarans ORDER BY ID";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.beginTransaction();
    try {
      final Cursor _cursor = __db.query(_statement);
      try {
        final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
        final int _cursorIndexOfIdUser = _cursor.getColumnIndexOrThrow("id_user");
        final int _cursorIndexOfPoli = _cursor.getColumnIndexOrThrow("poli");
        final int _cursorIndexOfKeluhan = _cursor.getColumnIndexOrThrow("keluhan");
        final int _cursorIndexOfTglRegis = _cursor.getColumnIndexOrThrow("tgl_regis");
        final int _cursorIndexOfPenyakitBawaan = _cursor.getColumnIndexOrThrow("penyakit_bawaan");
        final int _cursorIndexOfTinggiBadan = _cursor.getColumnIndexOrThrow("tinggi_badan");
        final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("status");
        final int _cursorIndexOfBeratBadan = _cursor.getColumnIndexOrThrow("berat_badan");
        final List<Pendaftaran> _result = new ArrayList<Pendaftaran>(_cursor.getCount());
        while(_cursor.moveToNext()) {
          final Pendaftaran _item;
          _item = new Pendaftaran();
          final String _tmpId;
          _tmpId = _cursor.getString(_cursorIndexOfId);
          _item.setId(_tmpId);
          final String _tmpId_user;
          _tmpId_user = _cursor.getString(_cursorIndexOfIdUser);
          _item.setId_user(_tmpId_user);
          final String _tmpPoli;
          _tmpPoli = _cursor.getString(_cursorIndexOfPoli);
          _item.setPoli(_tmpPoli);
          final String _tmpKeluhan;
          _tmpKeluhan = _cursor.getString(_cursorIndexOfKeluhan);
          _item.setKeluhan(_tmpKeluhan);
          final String _tmpTgl_regis;
          _tmpTgl_regis = _cursor.getString(_cursorIndexOfTglRegis);
          _item.setTgl_regis(_tmpTgl_regis);
          final String _tmpPenyakit_bawaan;
          _tmpPenyakit_bawaan = _cursor.getString(_cursorIndexOfPenyakitBawaan);
          _item.setPenyakit_bawaan(_tmpPenyakit_bawaan);
          final String _tmpTinggi_badan;
          _tmpTinggi_badan = _cursor.getString(_cursorIndexOfTinggiBadan);
          _item.setTinggi_badan(_tmpTinggi_badan);
          final String _tmpStatus;
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
          _item.setStatus(_tmpStatus);
          final String _tmpBerat_badan;
          _tmpBerat_badan = _cursor.getString(_cursorIndexOfBeratBadan);
          _item.setBerat_badan(_tmpBerat_badan);
          _result.add(_item);
        }
        __db.setTransactionSuccessful();
        return _result;
      } finally {
        _cursor.close();
        _statement.release();
      }
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Pendaftaran loadPendaftaranById(int sID) {
    final String _sql = "SELECT * FROM pendaftarans WHERE ID = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sID);
    __db.beginTransaction();
    try {
      final Cursor _cursor = __db.query(_statement);
      try {
        final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
        final int _cursorIndexOfIdUser = _cursor.getColumnIndexOrThrow("id_user");
        final int _cursorIndexOfPoli = _cursor.getColumnIndexOrThrow("poli");
        final int _cursorIndexOfKeluhan = _cursor.getColumnIndexOrThrow("keluhan");
        final int _cursorIndexOfTglRegis = _cursor.getColumnIndexOrThrow("tgl_regis");
        final int _cursorIndexOfPenyakitBawaan = _cursor.getColumnIndexOrThrow("penyakit_bawaan");
        final int _cursorIndexOfTinggiBadan = _cursor.getColumnIndexOrThrow("tinggi_badan");
        final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("status");
        final int _cursorIndexOfBeratBadan = _cursor.getColumnIndexOrThrow("berat_badan");
        final Pendaftaran _result;
        if(_cursor.moveToFirst()) {
          _result = new Pendaftaran();
          final String _tmpId;
          _tmpId = _cursor.getString(_cursorIndexOfId);
          _result.setId(_tmpId);
          final String _tmpId_user;
          _tmpId_user = _cursor.getString(_cursorIndexOfIdUser);
          _result.setId_user(_tmpId_user);
          final String _tmpPoli;
          _tmpPoli = _cursor.getString(_cursorIndexOfPoli);
          _result.setPoli(_tmpPoli);
          final String _tmpKeluhan;
          _tmpKeluhan = _cursor.getString(_cursorIndexOfKeluhan);
          _result.setKeluhan(_tmpKeluhan);
          final String _tmpTgl_regis;
          _tmpTgl_regis = _cursor.getString(_cursorIndexOfTglRegis);
          _result.setTgl_regis(_tmpTgl_regis);
          final String _tmpPenyakit_bawaan;
          _tmpPenyakit_bawaan = _cursor.getString(_cursorIndexOfPenyakitBawaan);
          _result.setPenyakit_bawaan(_tmpPenyakit_bawaan);
          final String _tmpTinggi_badan;
          _tmpTinggi_badan = _cursor.getString(_cursorIndexOfTinggiBadan);
          _result.setTinggi_badan(_tmpTinggi_badan);
          final String _tmpStatus;
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
          _result.setStatus(_tmpStatus);
          final String _tmpBerat_badan;
          _tmpBerat_badan = _cursor.getString(_cursorIndexOfBeratBadan);
          _result.setBerat_badan(_tmpBerat_badan);
        } else {
          _result = null;
        }
        __db.setTransactionSuccessful();
        return _result;
      } finally {
        _cursor.close();
        _statement.release();
      }
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Pendaftaran> loadPendaftaranByStatusPending(String status, int idUser) {
    final String _sql = "SELECT * FROM pendaftarans WHERE status = ? and id_user = ? order by ID desc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (status == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, status);
    }
    _argIndex = 2;
    _statement.bindLong(_argIndex, idUser);
    __db.beginTransaction();
    try {
      final Cursor _cursor = __db.query(_statement);
      try {
        final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
        final int _cursorIndexOfIdUser = _cursor.getColumnIndexOrThrow("id_user");
        final int _cursorIndexOfPoli = _cursor.getColumnIndexOrThrow("poli");
        final int _cursorIndexOfKeluhan = _cursor.getColumnIndexOrThrow("keluhan");
        final int _cursorIndexOfTglRegis = _cursor.getColumnIndexOrThrow("tgl_regis");
        final int _cursorIndexOfPenyakitBawaan = _cursor.getColumnIndexOrThrow("penyakit_bawaan");
        final int _cursorIndexOfTinggiBadan = _cursor.getColumnIndexOrThrow("tinggi_badan");
        final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("status");
        final int _cursorIndexOfBeratBadan = _cursor.getColumnIndexOrThrow("berat_badan");
        final List<Pendaftaran> _result = new ArrayList<Pendaftaran>(_cursor.getCount());
        while(_cursor.moveToNext()) {
          final Pendaftaran _item;
          _item = new Pendaftaran();
          final String _tmpId;
          _tmpId = _cursor.getString(_cursorIndexOfId);
          _item.setId(_tmpId);
          final String _tmpId_user;
          _tmpId_user = _cursor.getString(_cursorIndexOfIdUser);
          _item.setId_user(_tmpId_user);
          final String _tmpPoli;
          _tmpPoli = _cursor.getString(_cursorIndexOfPoli);
          _item.setPoli(_tmpPoli);
          final String _tmpKeluhan;
          _tmpKeluhan = _cursor.getString(_cursorIndexOfKeluhan);
          _item.setKeluhan(_tmpKeluhan);
          final String _tmpTgl_regis;
          _tmpTgl_regis = _cursor.getString(_cursorIndexOfTglRegis);
          _item.setTgl_regis(_tmpTgl_regis);
          final String _tmpPenyakit_bawaan;
          _tmpPenyakit_bawaan = _cursor.getString(_cursorIndexOfPenyakitBawaan);
          _item.setPenyakit_bawaan(_tmpPenyakit_bawaan);
          final String _tmpTinggi_badan;
          _tmpTinggi_badan = _cursor.getString(_cursorIndexOfTinggiBadan);
          _item.setTinggi_badan(_tmpTinggi_badan);
          final String _tmpStatus;
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
          _item.setStatus(_tmpStatus);
          final String _tmpBerat_badan;
          _tmpBerat_badan = _cursor.getString(_cursorIndexOfBeratBadan);
          _item.setBerat_badan(_tmpBerat_badan);
          _result.add(_item);
        }
        __db.setTransactionSuccessful();
        return _result;
      } finally {
        _cursor.close();
        _statement.release();
      }
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Pendaftaran> loadPendaftaranByStatusResponed(String status1, String status2) {
    final String _sql = "SELECT * FROM pendaftarans WHERE status = ? or status = ? order by ID desc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (status1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, status1);
    }
    _argIndex = 2;
    if (status2 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, status2);
    }
    __db.beginTransaction();
    try {
      final Cursor _cursor = __db.query(_statement);
      try {
        final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
        final int _cursorIndexOfIdUser = _cursor.getColumnIndexOrThrow("id_user");
        final int _cursorIndexOfPoli = _cursor.getColumnIndexOrThrow("poli");
        final int _cursorIndexOfKeluhan = _cursor.getColumnIndexOrThrow("keluhan");
        final int _cursorIndexOfTglRegis = _cursor.getColumnIndexOrThrow("tgl_regis");
        final int _cursorIndexOfPenyakitBawaan = _cursor.getColumnIndexOrThrow("penyakit_bawaan");
        final int _cursorIndexOfTinggiBadan = _cursor.getColumnIndexOrThrow("tinggi_badan");
        final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("status");
        final int _cursorIndexOfBeratBadan = _cursor.getColumnIndexOrThrow("berat_badan");
        final List<Pendaftaran> _result = new ArrayList<Pendaftaran>(_cursor.getCount());
        while(_cursor.moveToNext()) {
          final Pendaftaran _item;
          _item = new Pendaftaran();
          final String _tmpId;
          _tmpId = _cursor.getString(_cursorIndexOfId);
          _item.setId(_tmpId);
          final String _tmpId_user;
          _tmpId_user = _cursor.getString(_cursorIndexOfIdUser);
          _item.setId_user(_tmpId_user);
          final String _tmpPoli;
          _tmpPoli = _cursor.getString(_cursorIndexOfPoli);
          _item.setPoli(_tmpPoli);
          final String _tmpKeluhan;
          _tmpKeluhan = _cursor.getString(_cursorIndexOfKeluhan);
          _item.setKeluhan(_tmpKeluhan);
          final String _tmpTgl_regis;
          _tmpTgl_regis = _cursor.getString(_cursorIndexOfTglRegis);
          _item.setTgl_regis(_tmpTgl_regis);
          final String _tmpPenyakit_bawaan;
          _tmpPenyakit_bawaan = _cursor.getString(_cursorIndexOfPenyakitBawaan);
          _item.setPenyakit_bawaan(_tmpPenyakit_bawaan);
          final String _tmpTinggi_badan;
          _tmpTinggi_badan = _cursor.getString(_cursorIndexOfTinggiBadan);
          _item.setTinggi_badan(_tmpTinggi_badan);
          final String _tmpStatus;
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
          _item.setStatus(_tmpStatus);
          final String _tmpBerat_badan;
          _tmpBerat_badan = _cursor.getString(_cursorIndexOfBeratBadan);
          _item.setBerat_badan(_tmpBerat_badan);
          _result.add(_item);
        }
        __db.setTransactionSuccessful();
        return _result;
      } finally {
        _cursor.close();
        _statement.release();
      }
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Pendaftaran> loadPendaftaranMasuk(String status) {
    final String _sql = "SELECT * FROM pendaftarans WHERE status = ? order by ID desc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (status == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, status);
    }
    __db.beginTransaction();
    try {
      final Cursor _cursor = __db.query(_statement);
      try {
        final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
        final int _cursorIndexOfIdUser = _cursor.getColumnIndexOrThrow("id_user");
        final int _cursorIndexOfPoli = _cursor.getColumnIndexOrThrow("poli");
        final int _cursorIndexOfKeluhan = _cursor.getColumnIndexOrThrow("keluhan");
        final int _cursorIndexOfTglRegis = _cursor.getColumnIndexOrThrow("tgl_regis");
        final int _cursorIndexOfPenyakitBawaan = _cursor.getColumnIndexOrThrow("penyakit_bawaan");
        final int _cursorIndexOfTinggiBadan = _cursor.getColumnIndexOrThrow("tinggi_badan");
        final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("status");
        final int _cursorIndexOfBeratBadan = _cursor.getColumnIndexOrThrow("berat_badan");
        final List<Pendaftaran> _result = new ArrayList<Pendaftaran>(_cursor.getCount());
        while(_cursor.moveToNext()) {
          final Pendaftaran _item;
          _item = new Pendaftaran();
          final String _tmpId;
          _tmpId = _cursor.getString(_cursorIndexOfId);
          _item.setId(_tmpId);
          final String _tmpId_user;
          _tmpId_user = _cursor.getString(_cursorIndexOfIdUser);
          _item.setId_user(_tmpId_user);
          final String _tmpPoli;
          _tmpPoli = _cursor.getString(_cursorIndexOfPoli);
          _item.setPoli(_tmpPoli);
          final String _tmpKeluhan;
          _tmpKeluhan = _cursor.getString(_cursorIndexOfKeluhan);
          _item.setKeluhan(_tmpKeluhan);
          final String _tmpTgl_regis;
          _tmpTgl_regis = _cursor.getString(_cursorIndexOfTglRegis);
          _item.setTgl_regis(_tmpTgl_regis);
          final String _tmpPenyakit_bawaan;
          _tmpPenyakit_bawaan = _cursor.getString(_cursorIndexOfPenyakitBawaan);
          _item.setPenyakit_bawaan(_tmpPenyakit_bawaan);
          final String _tmpTinggi_badan;
          _tmpTinggi_badan = _cursor.getString(_cursorIndexOfTinggiBadan);
          _item.setTinggi_badan(_tmpTinggi_badan);
          final String _tmpStatus;
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
          _item.setStatus(_tmpStatus);
          final String _tmpBerat_badan;
          _tmpBerat_badan = _cursor.getString(_cursorIndexOfBeratBadan);
          _item.setBerat_badan(_tmpBerat_badan);
          _result.add(_item);
        }
        __db.setTransactionSuccessful();
        return _result;
      } finally {
        _cursor.close();
        _statement.release();
      }
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Pendaftaran> loadPendaftaranResponed(String status1, String status2) {
    final String _sql = "SELECT * FROM pendaftarans WHERE status = ? or status= ? order by ID desc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (status1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, status1);
    }
    _argIndex = 2;
    if (status2 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, status2);
    }
    __db.beginTransaction();
    try {
      final Cursor _cursor = __db.query(_statement);
      try {
        final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
        final int _cursorIndexOfIdUser = _cursor.getColumnIndexOrThrow("id_user");
        final int _cursorIndexOfPoli = _cursor.getColumnIndexOrThrow("poli");
        final int _cursorIndexOfKeluhan = _cursor.getColumnIndexOrThrow("keluhan");
        final int _cursorIndexOfTglRegis = _cursor.getColumnIndexOrThrow("tgl_regis");
        final int _cursorIndexOfPenyakitBawaan = _cursor.getColumnIndexOrThrow("penyakit_bawaan");
        final int _cursorIndexOfTinggiBadan = _cursor.getColumnIndexOrThrow("tinggi_badan");
        final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("status");
        final int _cursorIndexOfBeratBadan = _cursor.getColumnIndexOrThrow("berat_badan");
        final List<Pendaftaran> _result = new ArrayList<Pendaftaran>(_cursor.getCount());
        while(_cursor.moveToNext()) {
          final Pendaftaran _item;
          _item = new Pendaftaran();
          final String _tmpId;
          _tmpId = _cursor.getString(_cursorIndexOfId);
          _item.setId(_tmpId);
          final String _tmpId_user;
          _tmpId_user = _cursor.getString(_cursorIndexOfIdUser);
          _item.setId_user(_tmpId_user);
          final String _tmpPoli;
          _tmpPoli = _cursor.getString(_cursorIndexOfPoli);
          _item.setPoli(_tmpPoli);
          final String _tmpKeluhan;
          _tmpKeluhan = _cursor.getString(_cursorIndexOfKeluhan);
          _item.setKeluhan(_tmpKeluhan);
          final String _tmpTgl_regis;
          _tmpTgl_regis = _cursor.getString(_cursorIndexOfTglRegis);
          _item.setTgl_regis(_tmpTgl_regis);
          final String _tmpPenyakit_bawaan;
          _tmpPenyakit_bawaan = _cursor.getString(_cursorIndexOfPenyakitBawaan);
          _item.setPenyakit_bawaan(_tmpPenyakit_bawaan);
          final String _tmpTinggi_badan;
          _tmpTinggi_badan = _cursor.getString(_cursorIndexOfTinggiBadan);
          _item.setTinggi_badan(_tmpTinggi_badan);
          final String _tmpStatus;
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
          _item.setStatus(_tmpStatus);
          final String _tmpBerat_badan;
          _tmpBerat_badan = _cursor.getString(_cursorIndexOfBeratBadan);
          _item.setBerat_badan(_tmpBerat_badan);
          _result.add(_item);
        }
        __db.setTransactionSuccessful();
        return _result;
      } finally {
        _cursor.close();
        _statement.release();
      }
    } finally {
      __db.endTransaction();
    }
  }
}

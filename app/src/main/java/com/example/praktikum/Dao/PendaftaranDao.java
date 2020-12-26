package com.example.praktikum.Dao;

import com.example.praktikum.Model.Pendaftaran;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public interface PendaftaranDao {
    @Transaction
    @Query("SELECT * FROM pendaftarans ORDER BY ID")
    List<Pendaftaran> loadAllPendaftaranss();

    @Insert
    void insertPendaftaran(Pendaftaran pendaftaran);

    @Query("DELETE FROM pendaftarans")
    void deleteAll();

    @Transaction
    @Query("SELECT * FROM pendaftarans WHERE ID = :sID")
    Pendaftaran loadPendaftaranById(int sID);

    @Transaction
    @Query("SELECT * FROM pendaftarans WHERE status = :status and id_user = :idUser order by ID desc")
    List<Pendaftaran> loadPendaftaranByStatusPending(String
                                                        status, int idUser);
    @Transaction
    @Query("SELECT * FROM pendaftarans WHERE status = :status1 or status = :status2 order by ID desc")
    List<Pendaftaran> loadPendaftaranByStatusResponed(String
                                                                      status1, String status2);


    @Query("DELETE FROM pendaftarans where ID = :id")
    void deletePendaftaran(int id);

    //ADMIN
    @Transaction
    @Query("SELECT * FROM pendaftarans WHERE status = :status order by ID desc")
    List<Pendaftaran> loadPendaftaranMasuk(String status);

    @Query("UPDATE pendaftarans set tgl_regis = :tglRegis, status = :stat where ID = :sID")
    void acceptRegistrasi(String tglRegis, String stat, int sID);

    @Query("UPDATE pendaftarans set  status = :stat where ID = :sID")
    void refuseRegistrasi(String stat, int sID);

    @Transaction
    @Query("SELECT * FROM pendaftarans WHERE status = :status1 or status= :status2 order by ID desc")
    List<Pendaftaran> loadPendaftaranResponed(String status1, String status2);
}

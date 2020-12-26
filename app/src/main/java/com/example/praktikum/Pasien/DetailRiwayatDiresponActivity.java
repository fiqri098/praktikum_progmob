package com.example.praktikum.Pasien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.praktikum.Model.Pendaftaran;
import com.example.praktikum.R;

public class DetailRiwayatDiresponActivity extends AppCompatActivity {

    TextView keluhan;
    TextView penyakit;
    TextView poli;
    TextView tinggi;
    TextView berat;
    TextView status;
    TextView tanggal;
    TextView tanggalLayout;
    int position;
    ProgressDialog dialog1;
    String statusPasien, idRegis, tokenLogin, tanggalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayat_direspon);
        SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", getApplicationContext().MODE_PRIVATE);
        tokenLogin = userPref.getString("token", null);
        init();
    }

    private void init(){
        keluhan = (TextView)findViewById(R.id.txtKeluhan);
        penyakit = (TextView)findViewById(R.id.txtPenyakit);
        poli = (TextView)findViewById(R.id.txtPoli);
        tinggi = (TextView)findViewById(R.id.txtTinggi);
        berat = (TextView)findViewById(R.id.txtBerat);
        status = (TextView)findViewById(R.id.txtStatus);
        tanggal = (TextView)findViewById(R.id.txtTanggal);
        tanggalLayout = (TextView)findViewById(R.id.txtTanggalDetail);

        dialog1 = new ProgressDialog(DetailRiwayatDiresponActivity.this);
        dialog1.setCancelable(false);

        getIncomingExtra();
        setDetail();


    }

    private void getIncomingExtra() {

        if(getIntent().hasExtra("position")){
            position = getIntent().getIntExtra("position", 0);
        }
    }

    private void setDetail(){
        try {
            Pendaftaran pendaftaran = RiwayatDiresponActivity.pendaftaranDiresponList.get(position);
            keluhan.setText(pendaftaran.getKeluhan());
            penyakit.setText(pendaftaran.getPenyakit_bawaan());
            poli.setText(pendaftaran.getPoli());
            tinggi.setText(pendaftaran.getTinggi_badan());
            berat.setText(pendaftaran.getBerat_badan());
            statusPasien = pendaftaran.getStatus();
            idRegis = pendaftaran.getId();
            tanggalData = pendaftaran.getTgl_regis();
        } catch (Exception e) {
            Pendaftaran pendaftaran = RiwayatDiresponActivity.pendaftaranDiresponBackup.get(position);
            keluhan.setText(pendaftaran.getKeluhan());
            penyakit.setText(pendaftaran.getPenyakit_bawaan());
            poli.setText(pendaftaran.getPoli());
            tinggi.setText(pendaftaran.getTinggi_badan());
            berat.setText(pendaftaran.getBerat_badan());
            statusPasien = pendaftaran.getStatus();
            idRegis = pendaftaran.getId();
            tanggalData = pendaftaran.getTgl_regis();
        }



        if(statusPasien.equals("rejected")){
            status.setText("Rejected");
            tanggalLayout.setVisibility(View.GONE);
            tanggal.setVisibility(View.GONE);
        }else if(statusPasien.equals("accepted")){
            status.setText("Accepted");
            tanggal.setText(tanggalData);
        }

    }
}
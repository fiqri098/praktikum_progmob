package com.example.praktikum.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.praktikum.Model.Pendaftaran;
import com.example.praktikum.R;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDetailRwtActivity extends AppCompatActivity {

    TextView keluhan, penyakit, poli, tinggi, berat, status, tanggal, tanggalLayout;
    int position;
    String statusPasien, idRegis, tokenLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_rwt);
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
        getIncomingExtra();
        setDetail();

    }

    private void getIncomingExtra() {

        if(getIntent().hasExtra("position")){
            position = getIntent().getIntExtra("position", 0);
        }
    }

    private void setDetail(){
        Pendaftaran pendaftaran = AdminRiwayatActivity.pendaftaranList.get(position);
        keluhan.setText(pendaftaran.getKeluhan());
        penyakit.setText(pendaftaran.getPenyakit_bawaan());
        poli.setText(pendaftaran.getPoli());
        tinggi.setText(pendaftaran.getTinggi_badan());
        berat.setText(pendaftaran.getBerat_badan());
        statusPasien = pendaftaran.getStatus();
        idRegis = pendaftaran.getId();

        if(statusPasien.equals("pending")){
            status.setText("Pending");
            tanggalLayout.setVisibility(View.GONE);
            tanggal.setVisibility(View.GONE);
        }else if(statusPasien.equals("rejected")){
            status.setText("Rejected");
            tanggalLayout.setVisibility(View.GONE);
            tanggal.setVisibility(View.GONE);
        }else if(statusPasien.equals("accepted")){
            status.setText("Accepted");
            tanggal.setText(pendaftaran.getTgl_regis());
        }

    }

}

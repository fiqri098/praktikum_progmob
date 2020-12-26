package com.example.praktikum.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.praktikum.Adapter.AdminAdapterRiwayat;
import com.example.praktikum.Any.Constant;
import com.example.praktikum.Model.Pendaftaran;
import com.example.praktikum.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class AdminRiwayatActivity extends AppCompatActivity {

    public  static RecyclerView recyclerView;
    AdminAdapterRiwayat adminAdapterRiwayat;
    public static ArrayList<Pendaftaran> pendaftaranList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_riwayat);

        recyclerView = findViewById(R.id.recycler2);

        getPendaftaran();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_recent);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),AdminHomeActivity.class));
                        overridePendingTransition(0,0);
                        break;


                    case R.id.nav_recent:
                        overridePendingTransition(0,0);

                        break;


                    case R.id.nav_user:
                        startActivity(new Intent(getApplicationContext(),AdminProfileActivity.class));
                        overridePendingTransition(0,0);

                        break;

                }

                return false;
            }
        });
    }

    private void getPendaftaran(){
        pendaftaranList.clear();
        StringRequest request = new StringRequest(Request.Method.POST, Constant.GET_REGIS_DIRESPON, response -> {
            try {
                JSONObject object1 = new JSONObject(response);
                if (object1.getBoolean("success")) {
                    JSONArray user = new JSONArray(object1.getString("pendaftaran"));
                    for (int i=0; i<user.length(); i++){
                        JSONObject daftar = user.getJSONObject(i);

                        Pendaftaran pendaftaran = new Pendaftaran();
                        pendaftaran.setId(daftar.getString("id"));
                        pendaftaran.setBerat_badan(daftar.getString("berat_badan"));
                        pendaftaran.setPoli(daftar.getString("poli"));
                        pendaftaran.setId_user(daftar.getString("id_user"));
                        pendaftaran.setKeluhan(daftar.getString("keluhan"));
                        pendaftaran.setPenyakit_bawaan(daftar.getString("penyakit_bawaan"));
                        pendaftaran.setStatus(daftar.getString("status"));
                        pendaftaran.setTgl_regis(daftar.getString("tgl_regis"));
                        pendaftaran.setTinggi_badan(daftar.getString("tinggi_badan"));
                        pendaftaranList.add(pendaftaran);
                    }
                    adminAdapterRiwayat = new AdminAdapterRiwayat(pendaftaranList, getApplicationContext());
                    recyclerView.setAdapter(adminAdapterRiwayat);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Get Failed Failed", Toast.LENGTH_SHORT).show();
            }
        },error -> {
            error.printStackTrace();
        }){
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}

package com.example.praktikum.Pasien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.praktikum.Adapter.AdapterRegisDirespon;
import com.example.praktikum.Any.Constant;
import com.example.praktikum.Database.RoomDB;
import com.example.praktikum.Model.Pendaftaran;
import com.example.praktikum.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RiwayatDiresponActivity extends AppCompatActivity {

    public  static RecyclerView recyclerView2;
    AdapterRegisDirespon adapter2;
    private String tokenLogin;
    public static ArrayList<Pendaftaran> pendaftaranDiresponList = new ArrayList<>();
    public static ArrayList<Pendaftaran> pendaftaranDiresponBackup = new ArrayList<>();

    RoomDB database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_direspon);

        recyclerView2 = findViewById(R.id.recycler2);
        SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", getApplicationContext().MODE_PRIVATE);
        tokenLogin = userPref.getString("token", null);

        getPendaftaran();
    }

    private void getPendaftaran(){
        pendaftaranDiresponList.clear();
        database = RoomDB.getInstance(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Constant.GET_RESPONED_REGIS, response -> {
            try {
                JSONObject object1 = new JSONObject(response);
                if (object1.getBoolean("success")) {
                    database.pendaftaranDao().deleteAll();
                    JSONArray user = new JSONArray(object1.getString("pendaftaran"));
                    for (int i = 0; i < user.length(); i++) {
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
                        pendaftaranDiresponList.add(pendaftaran);
                        database.pendaftaranDao().insertPendaftaran(pendaftaran);
                    }
                    adapter2 = new AdapterRegisDirespon(pendaftaranDiresponList, getApplicationContext());
                    recyclerView2.setAdapter(adapter2);

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Get Failed Failed", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError || error instanceof AuthFailureError || error instanceof NoConnectionError || error instanceof TimeoutError) {
                    pendaftaranDiresponBackup = (ArrayList<Pendaftaran>) database.pendaftaranDao().loadPendaftaranByStatusResponed("accepted", "rejected");
                    adapter2 = new AdapterRegisDirespon(pendaftaranDiresponBackup, getApplicationContext());
                    recyclerView2.setAdapter(adapter2);
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                // Basic Authentication
                //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

                headers.put("Authorization", "Bearer " + tokenLogin);
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}
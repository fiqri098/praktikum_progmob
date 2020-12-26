package com.example.praktikum.Pasien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.praktikum.Adapter.AdapterRegisPending;
import com.example.praktikum.Any.Constant;
import com.example.praktikum.Model.Pendaftaran;
import com.example.praktikum.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RiwayatPendingActivity extends AppCompatActivity {

    public  static RecyclerView recyclerView;
    AdapterRegisPending adapter1;
    private String tokenLogin;
    public static ArrayList<Pendaftaran> pendaftaranList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayatregis);
        recyclerView = findViewById(R.id.recycler1);
        SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", getApplicationContext().MODE_PRIVATE);
        tokenLogin = userPref.getString("token", null);

        getPendaftaran();
    }

    private void getPendaftaran(){
        pendaftaranList.clear();
        StringRequest request = new StringRequest(Request.Method.POST, Constant.GET_REGIS_SAKIT, response -> {
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
                    adapter1 = new AdapterRegisPending(pendaftaranList, getApplicationContext());
                    recyclerView.setAdapter(adapter1);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Get Failed Failed", Toast.LENGTH_SHORT).show();
            }
        },error -> {
            error.printStackTrace();
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

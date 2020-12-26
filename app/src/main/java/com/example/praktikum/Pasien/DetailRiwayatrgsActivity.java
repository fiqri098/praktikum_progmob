package com.example.praktikum.Pasien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.praktikum.Any.Constant;
import com.example.praktikum.Model.Pendaftaran;
import com.example.praktikum.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailRiwayatrgsActivity extends AppCompatActivity {

    TextView keluhan, penyakit, poli, tinggi, berat, status, tanggal, tanggalLayout;
    Button btnEdit, btnDelete;
    int position;
    ProgressDialog dialog1;
    String statusPasien, idRegis, tokenLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayatrgs);
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

        btnEdit = (Button)findViewById(R.id.editrwtrgs);
        btnDelete = (Button)findViewById(R.id.delrwtrgs);

        dialog1 = new ProgressDialog(DetailRiwayatrgsActivity.this);
        dialog1.setCancelable(false);

        getIncomingExtra();
        setDetail();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailRiwayatrgsActivity.this);
                builder.setTitle("Confirm");
                builder.setMessage("Delete Registrasi?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog1.setMessage("Deleting Data");
                        dialog1.show();
                        StringRequest request = new StringRequest(Request.Method.POST, Constant.DELETE_REGIS_SAKIT, response -> {
                            try {
                                JSONObject object1 = new JSONObject(response);
                                if (object1.getBoolean("success")){
                                    RiwayatPendingActivity.recyclerView.getAdapter().notifyItemRemoved(position);
                                    RiwayatPendingActivity.recyclerView.getAdapter().notifyDataSetChanged();

                                    Intent intent = new Intent(DetailRiwayatrgsActivity.this, RiwayatPendingActivity.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(getApplicationContext(), "Delete Registrasi Success", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Delete Registrasi Failed", Toast.LENGTH_SHORT).show();
                            }
                            dialog1.dismiss();
                        },error -> {
                            error.printStackTrace();
                            dialog1.dismiss();
                        }){
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<>();
                                // Basic Authentication
                                //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

                                headers.put("Authorization", "Bearer " + tokenLogin);
                                return headers;
                            }

                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String,String> map = new HashMap<>();
                                map.put("id",idRegis);
                                return map;
                            }
                        };
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        queue.add(request);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailRiwayatrgsActivity.this, EditRegistrasiaActivity.class);
                intent.putExtra("id", idRegis);
                intent.putExtra("position", getIntent().getIntExtra("position", 0));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

    }

    private void getIncomingExtra() {

        if(getIntent().hasExtra("position")){
            position = getIntent().getIntExtra("position", 0);
        }
    }

    private void setDetail(){
        Pendaftaran pendaftaran = RiwayatPendingActivity.pendaftaranList.get(position);
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
            btnEdit.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }else if(statusPasien.equals("accepted")){
            status.setText("Accepted");
            tanggal.setText(pendaftaran.getTgl_regis());
            btnEdit.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }

    }


}

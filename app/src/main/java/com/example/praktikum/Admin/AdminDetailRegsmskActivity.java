package com.example.praktikum.Admin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDetailRegsmskActivity extends AppCompatActivity {

    TextView keluhan, penyakit, poli, tinggi, berat, status, tanggal, tanggalLayout;
    Button btnAcc, btnRef, btnAccept1, btnTanggal;
    int position;
    ProgressDialog dialog1;
    String statusPasien, idRegis, tokenLogin;
    EditText tanggalpemeriksaan;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_regsmsk);

        getIncomingExtra();
        init();

    }

    private void init(){
        dateFormatter = new SimpleDateFormat("YYYY-MM-dd", Locale.US);
        keluhan = (TextView)findViewById(R.id.txtKeluhan);
        penyakit = (TextView)findViewById(R.id.txtPenyakit);
        poli = (TextView)findViewById(R.id.txtPoli);
        tinggi = (TextView)findViewById(R.id.txtTinggi);
        berat = (TextView)findViewById(R.id.txtBerat);
        status = (TextView)findViewById(R.id.txtStatus);
        tanggal = (TextView)findViewById(R.id.txtTanggal);
        tanggalLayout = (TextView)findViewById(R.id.txtTanggalDetail);
        tanggalpemeriksaan = (EditText)findViewById(R.id.tanggal_accept);

        btnAcc = (Button)findViewById(R.id.accregis);
        btnRef = (Button)findViewById(R.id.tolakregis);

        dialog1 = new ProgressDialog(AdminDetailRegsmskActivity.this);
        dialog1.setCancelable(false);

        getIncomingExtra();
        setDetail();

        btnRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminDetailRegsmskActivity.this);
                builder.setTitle("Confirm");
                builder.setMessage("Reject Registrasi?");
                builder.setPositiveButton("Reject", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog1.setMessage("Rejecting Data");
                        dialog1.show();
                        StringRequest request = new StringRequest(Request.Method.POST, Constant.REJECT_REGIS_MASUK, response -> {
                            try {
                                JSONObject object1 = new JSONObject(response);
                                if (object1.getInt("success")==1){
                                    AdminRegismskActivity.recyclerView.getAdapter().notifyItemRemoved(position);
                                    AdminRegismskActivity.recyclerView.getAdapter().notifyDataSetChanged();

                                    Intent intent = new Intent(AdminDetailRegsmskActivity.this, AdminRegismskActivity.class);
                                    startActivity(intent);
                                    finish();
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Reject Registrasi Success", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Reject Registrasi Failed", Toast.LENGTH_SHORT).show();
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
        btnAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(AdminDetailRegsmskActivity.this);
                dialog.setContentView(R.layout.dialog_accept_regis);
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width,height);
                dialog.show();
                tanggalpemeriksaan = dialog.findViewById(R.id.tanggal_accept);
                btnAccept1 = dialog.findViewById(R.id.btnAccepted);
                btnTanggal = dialog.findViewById(R.id.btnTanggal);

                btnTanggal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDateDialog();
                    }
                });

                btnAccept1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StringRequest request = new StringRequest(Request.Method.POST, Constant.ACCEPT_REGIS_MASUK, response -> {
                            try {
                                JSONObject object1 = new JSONObject(response);
                                if (object1.getInt("success")==1){
                                    AdminRegismskActivity.recyclerView.getAdapter().notifyItemRemoved(position);
                                    AdminRegismskActivity.recyclerView.getAdapter().notifyDataSetChanged();

                                    Intent intent = new Intent(AdminDetailRegsmskActivity.this, AdminRegismskActivity.class);
                                    startActivity(intent);
                                    finish();
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Accept Registrasi Success", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Accept Registrasi Failed", Toast.LENGTH_SHORT).show();
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
                                map.put("tanggal", tanggalpemeriksaan.getText().toString());
                                return map;
                            }
                        };
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        queue.add(request);
                    }
                });

            }
        });

    }

    private void getIncomingExtra() {

        if(getIntent().hasExtra("position")){
            position = getIntent().getIntExtra("position", 0);
        }
    }

    private void setDetail(){
        Pendaftaran pendaftaran = AdminRegismskActivity.pendaftaranList.get(position);
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
            btnAcc.setVisibility(View.GONE);
            btnRef.setVisibility(View.GONE);
        }else if(statusPasien.equals("accepted")){
            status.setText("Accepted");
            tanggal.setText(pendaftaran.getTgl_regis());
            btnAcc.setVisibility(View.GONE);
            btnRef.setVisibility(View.GONE);
        }

    }

    private void showDateDialog() {
        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */

                tanggalpemeriksaan.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }
}

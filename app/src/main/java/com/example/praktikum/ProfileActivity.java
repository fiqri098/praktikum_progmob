package com.example.praktikum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.example.praktikum.Pasien.EditpassActivity;
import com.example.praktikum.Pasien.EditprofileActivity;
import com.example.praktikum.Pasien.RiwayatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private Button button, btnLogout;
    private TextView mainName, mainEmail, name, mobile, email, address, birthdate, gender, photo;
    private String idLogin, tokenLogin;
    CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", getApplicationContext().MODE_PRIVATE);
        idLogin = userPref.getString("id",null);
        tokenLogin = userPref.getString("token", null);
        isLogin();
        init();
    }

    private void init(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_user);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.nav_recent:
                        startActivity(new Intent(getApplicationContext(), RiwayatActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.nav_user:
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });

        mainName = (TextView)findViewById(R.id.txtMainNameProfile);
        mainEmail = (TextView)findViewById(R.id.txtMainEmailProfile);
        name = (TextView)findViewById(R.id.txtNameProfile);
        mobile = (TextView)findViewById(R.id.txtMobileProfile);
        email = (TextView)findViewById(R.id.txtEmailProfile);
        address = (TextView)findViewById(R.id.txtAddressProfile);
        birthdate = (TextView)findViewById(R.id.txtBirthdateProfile);
        gender = (TextView)findViewById(R.id.txtGenderProfile);
        circleImageView = (CircleImageView)findViewById(R.id.userPhoto);
        getUserInfo();
        setUserInfo();

        button = (Button) findViewById(R.id.buttonEditProfile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });

        button = (Button) findViewById(R.id.buttonEditPass);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity1();
            }
        });

        btnLogout = (Button) findViewById(R.id.buttonLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Confirm");
                builder.setMessage("Are You Sure to Logout?");
                builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = userPref.edit();
                        editor.clear();
                        editor.apply();
                        Toast.makeText(getApplicationContext(), "Logout Success", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(ProfileActivity.this, LoginActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);
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
    }

    public void openActivity() {
        Intent intent = new Intent(this, EditprofileActivity.class);
        startActivity(intent);
    }

    public void openActivity1() {
        Intent intent = new Intent(this, EditpassActivity.class);
        startActivity(intent);
    }

    private void getUserInfo() {
        StringRequest request = new StringRequest(Request.Method.POST, Constant.GET_USER, response -> {
            try {
                JSONObject object1 = new JSONObject(response);
                if (object1.getBoolean("success")){
                    JSONObject user = object1.getJSONObject("user");
                    SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", getApplicationContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = userPref.edit();
                    editor.putString("name", user.getString("name"));
                    editor.putString("email", user.getString("email"));
                    editor.putString("role", user.getString("role"));
                    editor.putString("mobile", user.getString("mobile"));
                    editor.putString("address", user.getString("address"));
                    editor.putString("gender", user.getString("gender"));
                    editor.putString("birthdate", user.getString("birthdate"));
                    editor.putString("photo", user.getString("photo"));
                    editor.apply();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Get Data Failed", Toast.LENGTH_SHORT).show();
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

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("id", idLogin);
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void setUserInfo(){
        SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", getApplicationContext().MODE_PRIVATE);
        String nameLogin = userPref.getString("name",null);
        String emailLogin = userPref.getString("email",null);
        String mobileLogin = userPref.getString("mobile",null);
        String addressLogin = userPref.getString("address",null);
        String genderLogin = userPref.getString("gender",null);
        String birthdateLogin = userPref.getString("birthdate",null);

        mainName.setText(nameLogin);
        mainEmail.setText(emailLogin);
        name.setText(nameLogin);
        mobile.setText(mobileLogin);
        email.setText(emailLogin);
        address.setText(addressLogin);
        birthdate.setText(birthdateLogin);
        gender.setText(genderLogin);
        Picasso.get().load(Constant.URL+"/profiles/"+userPref.getString("photo", null)).into(circleImageView);
    }

    private void isLogin(){
        SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        boolean isLoggedIn = userPref.getBoolean("isLoggedIn", false);

        if(!isLoggedIn){
            isFirstTime();
        }
    }

    public void isFirstTime(){
        SharedPreferences preferences = getApplication().getSharedPreferences("onBoard", Context.MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean("isFirstTime", true);

        if(isFirstTime){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstTime", false);
            editor.apply();

            startActivity(new Intent(ProfileActivity.this,ProfileActivity.class));
        }else{
            startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
        setUserInfo();
    }
}

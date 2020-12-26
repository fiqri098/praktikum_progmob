package com.example.praktikum.Admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.praktikum.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AdminHomeActivity extends AppCompatActivity {

    TextView welcomeMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", getApplicationContext().MODE_PRIVATE);

        welcomeMain = (TextView)findViewById(R.id.txtWelcomeMain);
        welcomeMain.setText("Halo "+userPref.getString("name", null)+"!\nSelamat Datang di Dashboard Admin");

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        overridePendingTransition(0,0);
                        break;


                    case R.id.nav_recent:
                        startActivity(new Intent(getApplicationContext(), AdminRiwayatActivity.class));
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
    public void registmsk(View view) {
        Intent intent = new Intent(this, AdminRegismskActivity.class);
        startActivity(intent);
    }

    public void listuser(View view) {
        Intent intent = new Intent(this, AdminListUserActivity.class);
        startActivity(intent);
    }

    public void listadmin(View view) {
        Intent intent = new Intent(this, AdminListAdminActivity.class);
        startActivity(intent);
    }
}

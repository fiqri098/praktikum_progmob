package com.example.praktikum.Any;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.praktikum.LoginActivity;
import com.example.praktikum.R;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        Intent intent = new Intent(AuthActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
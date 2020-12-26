package com.example.praktikum;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.praktikum.Admin.AdminHomeActivity;
import com.example.praktikum.Any.Constant;
import com.example.praktikum.Pasien.RegisterActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    TextView textView;
    EditText name, email,password, passwordConfirm;
    TextInputLayout layoutEmail, layoutPassword;
    Button btnlogin;
    ProgressDialog dialog;
    String fcm_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

    }

    private void init(){
        fcm_token = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = userPref.edit();
        editor.putString("fcm_token", fcm_token);
        editor.apply();
        layoutEmail = (TextInputLayout)findViewById(R.id.txtLayoutEmailSignIn);
        layoutPassword = (TextInputLayout)findViewById(R.id.txtLayoutPasswordSignIn);



        email = (EditText)findViewById(R.id.txtEmailSignIn);
        password = (EditText)findViewById(R.id.txtPasswordSignIn);
        btnlogin = (Button)findViewById(R.id.btnSignIn);
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setCancelable(false);

        textView = (TextView)findViewById(R.id.registernih);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    login();
                }
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!email.getText().toString().isEmpty()){
                    layoutEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (password.getText().length()>7){
                    layoutPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private boolean validate(){
        if(email.getText().toString().isEmpty()){
            layoutEmail.setErrorEnabled(true);
            layoutEmail.setError("Email is Required");
            return false;
        }
        if(password.getText().toString().length()<8){
            layoutPassword.setErrorEnabled(true);
            layoutPassword.setError("Password at least 8 characters");
            return false;
        }
        return true;

    }

    private void login(){
        dialog.setMessage("Logging in");
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Constant.LOGIN, response -> {
            try {
                JSONObject object1 = new JSONObject(response);
                if (object1.getBoolean("success")){
                    JSONObject user = object1.getJSONObject("user");
                    SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", getApplicationContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = userPref.edit();
                    editor.putString("token", object1.getString("token"));
                    editor.putString("id", String.valueOf(user.getInt("id")));
                    editor.putString("name", user.getString("name"));
                    editor.putString("email", user.getString("email"));
                    editor.putString("role", user.getString("role"));
                    editor.putString("mobile", user.getString("mobile"));
                    editor.putString("address", user.getString("address"));
                    editor.putString("gender", user.getString("gender"));
                    editor.putString("birthdate", user.getString("birthdate"));
                    editor.putString("photo", user.getString("photo"));
                    editor.putBoolean("isLoggedIn", true);
                    //editor.putString("password", user.getString("password"));
                    editor.apply();

                    String roleLogin = userPref.getString("role",null);

                    if(roleLogin.equals("2")){
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("admin");
                        Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent1);
                        Toast.makeText(getApplicationContext(), "Login User Success", Toast.LENGTH_SHORT).show();
                    }else if(roleLogin.equals("1")){
                        FirebaseMessaging.getInstance().subscribeToTopic("admin");
                        Intent intent2 = new Intent(LoginActivity.this, AdminHomeActivity.class);
                        startActivity(intent2);
                        Toast.makeText(getApplicationContext(), "Login Admin Success", Toast.LENGTH_SHORT).show();
                    }

                }else if(!object1.getBoolean("success")){
                    Toast.makeText(getApplicationContext(), "Login Failed, Check Email and Password", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        },error -> {
            error.printStackTrace();
            dialog.dismiss();
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("email",email.getText().toString().trim());
                map.put("password",password.getText().toString());
                map.put("fcm_token", fcm_token);
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}
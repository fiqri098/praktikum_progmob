package com.example.praktikum.Admin;

import android.os.Bundle;
import android.widget.TextView;

import com.example.praktikum.Any.Constant;
import com.example.praktikum.Model.User;
import com.example.praktikum.R;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdminDetailListadmActivity extends AppCompatActivity {

    TextView adminName, adminEmail, adminMobile, adminGender, adminAddress, adminBirthdate;
    int position;
    CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_listadm);

        init();
        getIncomingExtra();
        getAdmin();

    }

    private void init(){
        circleImageView = (CircleImageView)findViewById(R.id.userPhoto);
        adminName = (TextView)findViewById(R.id.Nameadmin1);
        adminEmail = (TextView)findViewById(R.id.Emailadmin1);
        adminMobile = (TextView)findViewById(R.id.Phoneadmin1);
        adminGender = (TextView)findViewById(R.id.Genderadmin1);
        adminAddress = (TextView)findViewById(R.id.Addressadmin1);
        adminBirthdate = (TextView)findViewById(R.id.birthadmin1);
    }

    private void getIncomingExtra() {
        if(getIntent().hasExtra("position")){

            position = getIntent().getIntExtra("position", 0);

        }
    }

    private void getAdmin(){
        User user = AdminListAdminActivity.userList.get(position);
        adminName.setText(user.getName());
        adminEmail.setText(user.getEmail());
        adminMobile.setText(user.getMobile());
        adminGender.setText(user.getGender());
        adminAddress.setText(user.getAddress());
        adminBirthdate.setText(user.getBirthdate());
        Picasso.get().load(Constant.URL+"/profiles/"+user.getPhoto()).into(circleImageView);
    }
}

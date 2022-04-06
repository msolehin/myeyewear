package com.example.myeyewear;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AccountActivity extends AppCompatActivity {

    Button accountSetting;
    Button logout,about,order;
    ImageButton back;

    public static TextView name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        name=(TextView)findViewById(R.id.nameProfile);
//        name.setText(Prevalent.currentOnlineUser.getName());
        name.setText(Prevalent.currentOnlineUser.getName());

        email=(TextView)findViewById(R.id.emailProfile);
        email.setText(Prevalent.currentOnlineUser.getEmail());

        order=(Button) findViewById(R.id.btnOrder);
        accountSetting=(Button) findViewById(R.id.btnAccountSetting);
        about=(Button) findViewById(R.id.btnAbout);
        logout=(Button) findViewById(R.id.btnLogout);

        back=(ImageButton) findViewById(R.id.btnBack4);


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent order = new Intent(AccountActivity.this, OrderActivity.class);
                startActivity(order);
            }
        });

        accountSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent accountSetting = new Intent(AccountActivity.this, AccountSettingActivity.class);
                startActivity(accountSetting);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent about = new Intent(AccountActivity.this, About.class);
                startActivity(about);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence options[]=new CharSequence[]{
                        "Yes",
                        "No"
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
                builder.setTitle("Are you sure you want to logout?");

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0){
                            Intent logout = new Intent(AccountActivity.this, login.class);
                            Prevalent.currentOnlineUser=null;
                            startActivity(logout);

                        }else if(i==1){

                        }

                    }
                });
                builder.show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent logout = new Intent(AccountActivity.this, HomeActivity.class);
                finish();
            }
        });

    }
}
package com.example.myeyewear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminHome extends AppCompatActivity {

    Button addProduct,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        addProduct = (Button) findViewById(R.id.btnLinkAdd);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addProduct = new Intent(AdminHome.this, AdminAddProduct.class);
                startActivity(addProduct);
            }
        });

       logout = (Button) findViewById(R.id.adminLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(AdminHome.this, login.class);
                startActivity(logout);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this, login.class));
        finish();
    }


}
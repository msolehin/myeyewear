package com.example.myeyewear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.FaceDetector;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Welcome2 extends AppCompatActivity {

    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome2);

        initComponents();
        clickListenHandler();
    }


    private void initComponents() {
        button2=findViewById(R.id.button2);
    }


    private void clickListenHandler(){
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login= new Intent(Welcome2.this, HomeActivity.class);
                startActivity(login);
            }
        });
    }


}
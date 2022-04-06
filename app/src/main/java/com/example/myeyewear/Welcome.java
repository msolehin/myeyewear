package com.example.myeyewear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initComponents();
        clickListenHandler();
    }

    private void initComponents() {
        button=findViewById(R.id.button);
    }

    private void clickListenHandler(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcome2 = new Intent(Welcome.this, Welcome2.class);
                startActivity(welcome2);
            }
        });
    }
}
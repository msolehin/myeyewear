package com.example.myeyewear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class OrderSuccessActivity extends AppCompatActivity {

    TextView date,oid,total;
    private String oID,dateR,totalL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        oID = getIntent().getStringExtra("oID");
        dateR = getIntent().getStringExtra("dateR");
        totalL = getIntent().getStringExtra("total");

        date=(TextView) findViewById(R.id.recDate);
        oid=(TextView) findViewById(R.id.recID);
        total=(TextView) findViewById(R.id.recTotal);

        date.setText(dateR);
        oid.setText(oID);
        total.setText("RM "+totalL);





        Button back=(Button) findViewById(R.id.btnConShop);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderSuccessActivity.this, HomeActivity.class));
                finish();
            }
        });



    }

    // disable back on login
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this, OrderSuccessActivity.class));
        finish();
    }
}
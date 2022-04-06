package com.example.myeyewear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private static final char MAX_LENGTH = 5;

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    Button placeOrder;
    TextView namephone,address,subtotal,total,totalp;

    private String totalAmount = "";
    private String radioSelected;

    private String oID,dateR,totalL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        totalAmount = getIntent().getStringExtra("Total Price = RM ");

        namephone=(TextView) findViewById(R.id.namePhoneDetail);
        address=(TextView) findViewById(R.id.addressDetail);
        subtotal=(TextView) findViewById(R.id.subtotal);
        total=(TextView) findViewById(R.id.totalPrice2);
        totalp=(TextView) findViewById(R.id.totalPrice);

        namephone.setText(Prevalent.currentOnlineUser.getName().toString()+" | "+Prevalent.currentOnlineUser.getPhone().toString());
        address.setText(Prevalent.currentOnlineUser.getAddress());

        subtotal.setText("RM "+totalAmount);
        total.setText("RM "+(Integer.parseInt(totalAmount)+10));
        totalp.setText("RM "+(Integer.parseInt(totalAmount)+10));


        ImageButton back=(ImageButton) findViewById(R.id.btnBackC);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConfirmFinalOrderActivity.this, CartActivity.class));
                finish();
            }
        });

        Button place=(Button) findViewById(R.id.btnPlaceOrder);
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.cod:

                        radioSelected="Cash on Delivery";
                        ConfirmOrder();
                        break;

                    case R.id.onlinebanking:
                        radioSelected="Online Banking";
                        ConfirmOrder();
                        break;

                    default:
                        Toast.makeText(ConfirmFinalOrderActivity.this, "Please choose payment method", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });
    }


    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    private static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString().toUpperCase();
    }

    private void ConfirmOrder() {

        final String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd MMM yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());
        final String generateKey= getRandomString(9);
       int total=Integer.parseInt(totalAmount)+10;
       totalL = String.valueOf(total);
       oID=generateKey;
       dateR=saveCurrentDate;


        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone())
                .child(generateKey);
                ;

        final HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("oid", generateKey);
        ordersMap.put("totalAmount", totalL);
        ordersMap.put("name", Prevalent.currentOnlineUser.getName().toString());
        ordersMap.put("phone", Prevalent.currentOnlineUser.getPhone().toString());
        ordersMap.put("address", Prevalent.currentOnlineUser.getAddress().toString());
        ordersMap.put("date", saveCurrentDate);
        ordersMap.put("time", saveCurrentTime);
        ordersMap.put("payment", radioSelected);
        ordersMap.put("status", "Successful");

        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Cart List")
                            .child("User View")
                            .child(Prevalent.currentOnlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
//                                        Toast.makeText(ConfirmFinalOrderActivity.this, "Your order has been placed successfullly", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(ConfirmFinalOrderActivity.this, OrderSuccessActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.putExtra("dateR",String.valueOf(dateR));
                                        intent.putExtra("oID",String.valueOf(oID));
                                        intent.putExtra("total",String.valueOf(totalL));
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });


    }
}
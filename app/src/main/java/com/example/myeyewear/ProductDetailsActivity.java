package com.example.myeyewear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

    private Button addToCartBtn,tryOn;
    ImageButton back;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productPrice, productDescription, productName;
    private String productID="";
    public static String proid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productID = getIntent().getStringExtra("pid");
        proid=productID.toString();



        addToCartBtn=(Button)findViewById(R.id.btnAddtoCart);
        numberButton=(ElegantNumberButton) findViewById(R.id.btnNumber);
        productImage=(ImageView) findViewById (R.id.productImageDetail);
        productName = (TextView) findViewById (R.id.productNameDetail);
        productDescription= (TextView) findViewById(R.id.productDescDetail);
        productPrice = (TextView) findViewById(R.id.productPriceDetail);

        getProductDetails(productID);

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Prevalent.currentOnlineUser==null){
                    CharSequence options[]=new CharSequence[]{
                            "Login"
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetailsActivity.this);
                    builder.setTitle("Please login first");

                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(i==0){
                                Intent login= new Intent(ProductDetailsActivity.this, login.class);
                                startActivity(login);
                            }

                        }
                    });
                    builder.show();



                }else{
                    addingToCartList();
                }



            }
        });
        tryOn=(Button) findViewById(R.id.btnTryon);
        tryOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ar= new Intent(ProductDetailsActivity.this, FaceFilterActivity.class);
                startActivity(ar);

            }
        });

        back=(ImageButton) findViewById(R.id.btnBack5);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void addingToCartList(){

        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate= Calendar.getInstance();
        SimpleDateFormat currentDate= new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());

        DatabaseReference cartListRef = FirebaseDatabase .getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", productID);
        cartMap.put("pname",productName.getText().toString());
        cartMap.put("price",productPrice.getText().toString());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("quantity", numberButton.getNumber());
        cartMap.put("discount","");

        cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone())
                .child("Products").child(productID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone())
                                    .child("Products").child(productID)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                Toast.makeText(ProductDetailsActivity.this,"Product added to cart", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(ProductDetailsActivity.this,CartActivity.class);
                                                startActivity(intent);

                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    private void getProductDetails(String productID){

        DatabaseReference productsRef= FirebaseDatabase.getInstance().getReference().child("Products");

        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Products products = dataSnapshot.getValue(Products.class);

                    productName.setText(products.getPname());
//                    productName.setText(proid);

                    productPrice.setText("RM "+products.getPrice());
                    productDescription.setText(products.getDescription());

                    Picasso.get().load(products.getImage()).into(productImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
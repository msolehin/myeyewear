package com.example.myeyewear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class  login extends AppCompatActivity {

    TextView textView;

    EditText InputPhoneNumber, InputPassword;

    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textView = (TextView) findViewById(R.id.linkRegister);

        InputPhoneNumber = (EditText) findViewById(R.id.phone);
        InputPassword = (EditText) findViewById(R.id.password);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(login.this, Register.class);
                startActivity(login);
            }
        });
    }

    public void loginUser(View v) {
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if (phone.isEmpty()) {
            InputPhoneNumber.setError("Phone is required");
            InputPhoneNumber.requestFocus();

        } else if (password.isEmpty()) {
            InputPassword.setError("Password is required");
            InputPassword.requestFocus();

        } else {
            AllowAccessToAccount(phone, password);
        }
    }

    //admin login verify
    public void loginAdmin(View v) {

        startActivity(new Intent(this, HomeActivity.class));
        finish();
//        parentDbName = "Admin";
//        String phone = InputPhoneNumber.getText().toString();
//        String password = InputPassword.getText().toString();
//
//        if (phone.isEmpty()) {
//            InputPhoneNumber.setError("Phone is required");
//            InputPhoneNumber.requestFocus();
//
//        } else if (password.isEmpty()) {
//            InputPassword.setError("Password is required");
//            InputPassword.requestFocus();
//
//        } else {
//            final DatabaseReference RootRef;
//            RootRef = FirebaseDatabase.getInstance().getReference();
//
//            RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.child(parentDbName).child(phone).exists()) {
//                        User usersData = dataSnapshot.child(parentDbName).child(phone).getValue(User.class);
//
//                        if (usersData.getPhone().equals(phone)) {
//                            if (usersData.getPassword().equals(password)) {
//                                if (parentDbName.equals("Admin")) {
//                                    Toast.makeText(login.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();
//
//                                    Intent intent = new Intent(login.this, AdminHome.class);
//                                    startActivity(intent);
//                                }
//                            } else {
//                                Toast.makeText(login.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    } else {
//                        Toast.makeText(login.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        }
    }


    //user login verify
    private void AllowAccessToAccount(final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phone).exists()) {
                    User usersData = dataSnapshot.child(parentDbName).child(phone).getValue(User.class);

                    if (usersData.getPhone().equals(phone)) {
                        if (usersData.getPassword().equals(password)) {
                            if (parentDbName.equals("Users")) {
                                Toast.makeText(login.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(login.this, HomeActivity.class);
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(login.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(login.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

// disable back on login
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this, login.class));
        finish();
    }


}
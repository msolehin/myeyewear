package com.example.myeyewear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.example.myeyewear.User;
import com.example.myeyewear.Prevalent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    private EditText InputEmail, InputName, InputPhone, InputAddress, InputPassword;
    private Button btnRegister;
    private TextView textView;


    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        InputName = (EditText)findViewById(R.id.regName);
        InputEmail = (EditText) findViewById(R.id.regEmail);
        InputPhone = (EditText)findViewById(R.id.regPhone);
        InputAddress = (EditText)findViewById(R.id.regAddress);
        InputPassword = (EditText)findViewById(R.id.regPass);

        //redirect register to login page
        textView = (TextView) findViewById(R.id.linkLogin);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(Register.this, login.class);
                startActivity(register);
            }
        });
    }

    public void createUser(View v) {
        String email = InputEmail.getText().toString();
        String name = InputName.getText().toString();
        String phone = InputPhone.getText().toString();
        String address = InputAddress.getText().toString();
        String password = InputPassword.getText().toString();

        if (name.isEmpty()) {
            InputName.setError("Name is required");
            InputName.requestFocus();

        } else if (email.isEmpty()) {
            InputEmail.setError("Email is required");
            InputEmail.requestFocus();

        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            InputEmail.setError("Please provide valid email!");
            InputEmail.requestFocus();

        } else if (address.isEmpty()) {
            InputAddress.setError("Address is required");
            InputAddress.requestFocus();

        } else if (phone.isEmpty()) {
            InputPhone.setError("Phone is required");
            InputPhone.requestFocus();

        } else if (phone.length()<10 ) {
            InputPhone.setError("Phone no must be 10-11 numbers");
            InputPhone.requestFocus();

        }
        else if (phone.length()>11) {
            InputPhone.setError("Phone no must be 10-11 numbers");
            InputPhone.requestFocus();

        }
        else if (password.isEmpty()) {
            InputPassword.setError("Password is required");
            InputPassword.requestFocus();

        } else if (password.length()<6) {
            InputPassword.setError("Password is less than 6");
            InputPassword.requestFocus();

        }else {
            Validate(name, email, phone, address, password);

        }
    }

    private void Validate(final String name, final String email, final String phone, final String address, final String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(phone).exists())) {
                    HashMap<String, Object> userdataMap = new HashMap<>();

                    userdataMap.put("name", name);
                    userdataMap.put("email", email);
                    userdataMap.put("phone", phone);
                    userdataMap.put("address", address);
                    userdataMap.put("password", password);

                    RootRef.child("Users").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Register.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(Register.this, login.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(Register.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(Register.this, "This " + phone + " already exists.", Toast.LENGTH_SHORT).show();

                    Toast.makeText(Register.this, "Please try again using another phone number.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Register.this, HomeActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}



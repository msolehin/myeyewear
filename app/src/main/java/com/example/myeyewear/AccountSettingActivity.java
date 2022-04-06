package com.example.myeyewear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;

public class AccountSettingActivity extends AppCompatActivity {

    ImageButton back;

    public EditText fullNameEditText, userEmailEditText,userPhoneEditText,userPasswordEditText, addressEditText;
    private TextView saveTextButton;


    private String checker = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);


        fullNameEditText = (EditText) findViewById(R.id.setting_name);
        userPhoneEditText = (EditText) findViewById(R.id.setting_phone);
        userEmailEditText = (EditText) findViewById(R.id.setting_email);
        userPasswordEditText = (EditText) findViewById(R.id.setting_password);
        addressEditText = (EditText) findViewById(R.id.setting_address);

        saveTextButton = (TextView) findViewById(R.id.btnUpdateAccount);

        userInfoDisplay(fullNameEditText, userEmailEditText,userPhoneEditText, userPasswordEditText,addressEditText);
        userPhoneEditText.setFocusable(false);

        saveTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                userInfoSaved();

//                if (checker.equals("clicked")) {
//                    userInfoSaved();
//                }
//                else {
//                    updateOnlyUserInfo();
//                    Prevalent.currentOnlineUser.setName(fullNameEditText.getText().toString());
//                    AccountActivity.name.setText(fullNameEditText.getText().toString());
//                    HomeActivity.welcome.setText("Welcome "+fullNameEditText.getText().toString());
//
//                    Prevalent.currentOnlineUser.setEmail(userEmailEditText.getText().toString());
//                    AccountActivity.email.setText(userEmailEditText.getText().toString());
//
//                }
            }
        });

        back=(ImageButton) findViewById(R.id.btnBack3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void updateOnlyUserInfo()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

        HashMap<String, Object> userMap = new HashMap<>();
        userMap. put("name", fullNameEditText.getText().toString());
        userMap. put("email", userEmailEditText.getText().toString());
        userMap. put("address", addressEditText.getText().toString());
        userMap. put("phoneOrder", userPhoneEditText.getText().toString());
        userMap. put("password", userPasswordEditText.getText().toString());
        ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);

//        startActivity(new Intent(AccountSettingActivity.this, AccountSettingActivity.class));
        Toast.makeText(AccountSettingActivity.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();


//        finish();
    }

    private void userInfoSaved()
    {
        if (TextUtils.isEmpty(fullNameEditText.getText().toString())) {
            fullNameEditText.setError("Name is required");
            fullNameEditText.requestFocus();

        } else if (TextUtils.isEmpty(userEmailEditText.getText().toString())) {
            userEmailEditText.setError("Email is required");
            userEmailEditText.requestFocus();

        } else if (!Patterns.EMAIL_ADDRESS.matcher(userEmailEditText.getText().toString()).matches()) {
            userEmailEditText.setError("Please provide valid email!");
            userEmailEditText.requestFocus();

        } else if (TextUtils.isEmpty(addressEditText.getText().toString())) {
            addressEditText.setError("Address is required");
            addressEditText.requestFocus();

        }  else if (TextUtils.isEmpty(userPasswordEditText.getText().toString())) {
            userPasswordEditText.setError("Password is required");
            userPasswordEditText.requestFocus();

        }else if ((userPasswordEditText.getText().toString()).length()<6) {
            userPasswordEditText.setError("Password is less than 6");
            userPasswordEditText.requestFocus();
        }else{
            updateOnlyUserInfo();
            Prevalent.currentOnlineUser.setName(fullNameEditText.getText().toString());
            AccountActivity.name.setText(fullNameEditText.getText().toString());
            HomeActivity.welcome.setText("Welcome "+fullNameEditText.getText().toString());

            Prevalent.currentOnlineUser.setEmail(userEmailEditText.getText().toString());
            AccountActivity.email.setText(userEmailEditText.getText().toString());

        }
    }

    private void userInfoDisplay( final EditText fullNameEditText,final EditText userEmailEditText, final EditText userPhoneEditText, final EditText userPasswordEditText,final EditText addressEditText)
    {
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone());

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.child("phone").exists())
                    {
                        String name = dataSnapshot.child("name").getValue().toString();
                        String email = dataSnapshot.child("email").getValue().toString();
                        String phone = dataSnapshot.child("phone").getValue().toString();
                        String password = dataSnapshot.child("password").getValue().toString();
                        String address = dataSnapshot.child("address").getValue().toString();

                        fullNameEditText.setText(name);
                        userEmailEditText.setText(email);
                        userPhoneEditText.setText(phone);
                        userPasswordEditText.setText(password);
                        addressEditText.setText(address);

                        Prevalent.currentOnlineUser.setName(name);
                        Prevalent.currentOnlineUser.setEmail(email);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
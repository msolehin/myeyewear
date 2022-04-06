package com.example.myeyewear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.myeyewear.User;
import com.example.myeyewear.Prevalent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

//        String UserPhoneKey = Prevalent.UserPhoneKey;
//        String UserPasswordKey = Prevalent.UserPasswordKey;
//
//        //check the last logged in user
//
//        if (UserPhoneKey != "" && UserPasswordKey != "")
//        {
//            if (!TextUtils.isEmpty(UserPhoneKey)  &&  !TextUtils.isEmpty(UserPasswordKey))
//            {
//                AllowAccess(UserPhoneKey, UserPasswordKey);
//            }
//        }

        //splash screen timeout
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Splash.this,Welcome.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }

    //check if already login or not
//    private void AllowAccess(final String phone, final String password)
//    {
//        final DatabaseReference RootRef;
//        RootRef = FirebaseDatabase.getInstance().getReference();
//
//
//        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//            {
//                if (dataSnapshot.child("Users").child(phone).exists())
//                {
//                    User usersData = dataSnapshot.child("Users").child(phone).getValue(User.class);
//
//                    if (usersData.getPhone().equals(phone))
//                    {
//                        if (usersData.getPassword().equals(password))
//                        {
//                            Toast.makeText(Splash.this, "Please wait, you are already logged in...", Toast.LENGTH_SHORT).show();
//
//                            Intent intent = new Intent(Splash.this, HomeActivity.class);
//                            Prevalent.currentOnlineUser = usersData;
//                            startActivity(intent);
//                        }
//                        else
//                        {
//                            Toast.makeText(Splash.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
////                else
////                {
////                    Toast.makeText(Splash.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
////
////                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

}

package com.meass.professionalworks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MultiDex.install(this);
        firebaseAuth=FirebaseAuth.getInstance();
        Handler handler=new Handler();
        Thread thread=new Thread();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (firebaseAuth.getCurrentUser()!=null) {
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                }
                else {
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                }
            }
        },50000);
    }
}
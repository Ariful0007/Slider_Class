package com.meass.professionalworks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;

public class MainActivity2 extends AppCompatActivity {
String myfirstkey11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        try {
            myfirstkey11=getIntent().getStringExtra("myfirstkey");
        }catch (Exception e) {
            myfirstkey11=getIntent().getStringExtra("myfirstkey");
        }
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Home Page");
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
    }


}
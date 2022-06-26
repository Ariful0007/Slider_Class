package com.meass.professionalworks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class FoegetPassword extends AppCompatActivity {
EditText email;
FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foeget_password);
        email=findViewById(R.id.email);
        Button submit=findViewById(R.id.submit);
        firebaseAuth=FirebaseAuth.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useremail=email.getText().toString();
                if (TextUtils.isEmpty(useremail)) {
                    Toasty.info(getApplicationContext(),"Error",Toasty.LENGTH_SHORT,true).show();
                }
                else {
                    firebaseAuth.confirmPasswordReset(useremail,"12344444")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                }
            }
        });

    }
}
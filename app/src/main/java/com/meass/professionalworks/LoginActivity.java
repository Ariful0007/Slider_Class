package com.meass.professionalworks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {
EditText email,password;
FirebaseFirestore firebaseFirestore;
FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        Button submit=findViewById(R.id.submit);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useremail=email.getText().toString();
                String userpassword=password.getText().toString();
                if (TextUtils.isEmpty(useremail)|| TextUtils.isEmpty(userpassword))
                {
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
                else {
                    ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);
                    progressDialog.setTitle("Loading");
                    progressDialog.setMessage("Please wait");
                    progressDialog.show();
firebaseFirestore.collection("Users")
        .document(useremail)
        .get()
        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        String user_password=task.getResult().getString("password");
                        if (userpassword.equals(user_password)) {
                            firebaseAuth.signInWithEmailAndPassword(useremail,user_password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                progressDialog.dismiss();
                                                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    else {
                        progressDialog.dismiss();
                        Toasty.info(getApplicationContext(),"User not found",Toasty.LENGTH_SHORT,true).show();
                    }
                }
                else {
                    progressDialog.dismiss();
                    Toasty.info(getApplicationContext(),"User not found",Toasty.LENGTH_SHORT,true).show();
                }
            }
        });
                }
            }
        });

    }

    public void register(View view) {
        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
    }
}
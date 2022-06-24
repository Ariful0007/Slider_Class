package com.meass.professionalworks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
EditText fullname,email,password;
Button submit;
FirebaseAuth firebaseAuth;
ProgressDialog progressDialog;
FirebaseFirestore firebaseFirestore;
Spinner spinnar,secondspinner;
String[] gender={"Select a gender ","Male","Female","Others"};
//a[]={1,0,0,0};

    // 0 1 2 3
    // 0 0 1 0
    ImageView profileImage;
    Uri imageUri;

String value_gender;
private int CHOSSE_IMAGE=1;
StorageReference storageReference;
FirebaseStorage firebaseStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fullname=findViewById(R.id.fullname);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        submit=findViewById(R.id.submit);
        profileImage=findViewById(R.id.profileImage);
        firebaseStorage= FirebaseStorage.getInstance();
        storageReference=FirebaseStorage.getInstance().getReference();
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select an image"),11);

            }
        });
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        spinnar=findViewById(R.id.spinnar);
        secondspinner=findViewById(R.id.spinnar1);
       // secondspinner.setOnItemSelectedListener(this);
        spinnar.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.textitem,gender);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnar.setAdapter(arrayAdapter);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userfullname=fullname.getText().toString();
                String useremail=email.getText().toString();
                String userpassword=password.getText().toString();
                if (TextUtils.isEmpty(userfullname)|| TextUtils.isEmpty(useremail)||TextUtils.isEmpty(userpassword)) {
                    Toasty.error(getApplicationContext(),"Something is error",Toasty.LENGTH_SHORT,true).show();
                    return;
                }
                else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Confirmation")
                            .setMessage("Are you want to register in this application?")
                            .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            progressDialog=new ProgressDialog(RegisterActivity.this);
                            progressDialog.setTitle("Loading");
                            progressDialog.show();
                            firebaseAuth.createUserWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        UserModel userModel=new UserModel(userfullname,useremail,userpassword,value_gender);
                                        firebaseFirestore.collection("Users")
                                                .document(firebaseAuth.getCurrentUser().getEmail())
                                                .set(userModel)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            progressDialog.dismiss();
                                                            Toast.makeText(RegisterActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).create().show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==11 && resultCode==RESULT_OK && data.getData()!=null && data.getData()!=null) {
            imageUri=data.getData();
           try {
               Bitmap imageBitmap= MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
               profileImage.setImageBitmap(imageBitmap);
               upload(imageUri);
           }catch (Exception e) {
               e.printStackTrace();
           }
        }

    }

    private void upload(Uri imageUri) {
        if (imageUri!=null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            StorageReference ref = storageReference.child("Image/"+ UUID.randomUUID().toString());
            ref.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            final Uri downloadUri=uriTask.getResult();



                            if (uriTask.isSuccessful()) {
String downmainImageuri=downloadUri.toString();
progressDialog.dismiss();




                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        value_gender=adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
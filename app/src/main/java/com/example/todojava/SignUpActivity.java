package com.example.todojava;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todojava.Model.UserModel;
import com.example.todojava.databinding.ActivityMainBinding;
import com.example.todojava.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignupBinding binding;

    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding=ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        progressDialog=new ProgressDialog(this);

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=binding.etName.getText().toString();
                String email=binding.etEmail.getText().toString().trim();
                String password=binding.etPassword.getText().toString();

                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                progressDialog.cancel();

                                firebaseFirestore.collection("User")
                                        .document(FirebaseAuth.getInstance().getUid())
                                        .set(new UserModel(name,email));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                        });

            }
        });
        binding.goToLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }
        });
    }


}

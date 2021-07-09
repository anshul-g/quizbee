package com.example.android.quizbee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.quizbee.MainApp.MainActivity;
import com.example.android.quizbee.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class Register extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore database;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private EditText editTextUniversity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextUniversity = findViewById(R.id.editTextUniversity);

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
    }

    // Defining registration process
    public void signup_account(View view) {

        String email = editTextEmail.getText().toString();
        String pass = editTextPassword.getText().toString();
        String Name = editTextName.getText().toString();
        String OrgName = editTextUniversity.getText().toString();

        if (Name.isEmpty()) {
            Toast.makeText(Register.this, "Enter your name!", Toast.LENGTH_SHORT).show();
            return;
        } else if (email.isEmpty()) {
            Toast.makeText(Register.this, "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        } else if (pass.isEmpty()) {
            Toast.makeText(Register.this, "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        } else if (OrgName.isEmpty()) {
            Toast.makeText(Register.this, "Enter Organisation Name!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    String userid = auth.getCurrentUser().getUid();
                    Users user = new Users(email,pass,Name,OrgName);

                    database.collection("users").document(userid).set(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Register.this, "Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Register.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                            }
                            else {
                                Toast.makeText(Register.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(Register.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}


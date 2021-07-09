package com.example.android.quizbee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.quizbee.models.Users;
import com.example.android.quizbee.MainApp.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class Login extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore database;
    private Users user;
    public EditText editTextEmail;
    public EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // checking if the user is already logged in
        user = new Users();
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }

        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.edittext_email);
        editTextPassword = findViewById(R.id.edittext_password);

        //Hiding ActionBar
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
    }


    // Method to define what happens when user clicks on login
    public void login(View view) {
        String email = editTextEmail.getText().toString();
        String pass = editTextPassword.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pass.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                }
                else{
                    Toast.makeText(Login.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // defining fuctions to switch between activities
    public void signup(View view) {
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }

    public void forgot_password(View view) {
        Intent intent = new Intent(this,ResetPassword.class);
        startActivity(intent);
    }
}
package com.example.android.quizbee.MainApp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.quizbee.Login;
import com.example.android.quizbee.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Account extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore database;
    private String UserId;
    private TextView username;
    private TextView userEmail;
    private TextView userOrg;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


        username = findViewById(R.id.Username);
        userEmail = findViewById(R.id.userEmail);
        userOrg = findViewById(R.id.userOrg);
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        UserId = auth.getCurrentUser().getUid();

        // Accessing user information from database
        // and finally showing them to user on Account page
        database.collection("users").document(UserId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String fullname = documentSnapshot.getString("name");
                        String email = documentSnapshot.getString("email");
                        String orgname = documentSnapshot.getString("orgName");

                        username.setText(fullname);
                        Log.d("organisation",orgname);
                        userEmail.setText(email);
                        userOrg.setText(orgname);
                    }
                });
    }

    // Logout button method
    public void signOut(View view) {
        auth.signOut();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finishAffinity();
    }

    // Delete Account button method
    public void deleteUser(View view) {
        user = auth.getCurrentUser();
        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(Account.this,Login.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(Account.this,"Unsuccesful",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}


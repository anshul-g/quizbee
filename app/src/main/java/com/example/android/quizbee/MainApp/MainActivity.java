package com.example.android.quizbee.MainApp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.quizbee.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore database;
    String UserId;
    public TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.WelcomeText);
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        UserId = auth.getCurrentUser().getUid();

        // displaying user's first name
        database.collection("users").document(UserId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String fullname = documentSnapshot.getString("name");
                        String[] name = fullname.split(" ");

                        username.setText("Welcome "+"\n"+name[0]+"!");
                        Log.d("USERNAME", name[0]);
                    }
                });
    }

    //Defining functions for starting new activities by clicking on cardviews

    public void courses_click(View view) {
        Intent intent1 = new Intent(this, Quizzes.class);
        startActivity(intent1);
    }

    public void grades_click(View view) {
        Intent intent2 = new Intent(this, Grades.class);
        startActivity(intent2);
    }

    public void myprofile_click(View view) {
        Intent intent3 = new Intent(this, Account.class);
        startActivity(intent3);
    }
}
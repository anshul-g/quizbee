package com.example.android.quizbee.MainApp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.quizbee.R;
import com.example.android.quizbee.adapters.QuizAdapter;
import com.example.android.quizbee.models.Quiz;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Quizzes extends AppCompatActivity {

    public String quiz_id;
    private static ArrayList<Quiz> quizList = new ArrayList<Quiz>();
    private RecyclerView recyclerView;
    private QuizAdapter quizAdapter;
    private FirebaseFirestore database;
    static ArrayList<Quiz> tempList = new ArrayList<>();;
    static ArrayList<String> tempName = new ArrayList<>();;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        recyclerView = findViewById(R.id.recyclerViewTeacher);
        database = FirebaseFirestore.getInstance();
        bindViews();
    }

    // floating button functionality
    public void addQuiz(View view) {
        MaterialAlertDialogBuilder quizid = new MaterialAlertDialogBuilder(Quizzes.this,R.style.Theme_Quizbee);
        quizid.setTitle("Please enter your Quiz ID");

        EditText idInput = new EditText(Quizzes.this);
        idInput.setInputType(InputType.TYPE_CLASS_TEXT);
        quizid.setView(idInput);

        quizid.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                quiz_id = idInput.getText().toString();
                bindViews();

            }
        });

        quizid.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // retrieving window and defining parameters for dialog box
        quizid.show().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    private void bindViews() {
        database.collection("quizzes").whereEqualTo("id",quiz_id)
                .addSnapshotListener((value, e) -> {

                    tempList = (ArrayList<Quiz>) value.toObjects(Quiz.class);

                    if (quizList.size()!=0) {
                        for (int i = 0; i < quizList.size(); i++) {
                            tempName.add(quizList.get(i).getName());
                        }
                        if (tempList.size()!= 0) {
                            if (tempName.contains(tempList.get(0).getName())) {
                                Toast.makeText(this, "Quiz already added!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                quizList.addAll(tempList);
                                quizAdapter = new QuizAdapter(Quizzes.this, quizList);
                                recyclerView.setLayoutManager(new LinearLayoutManager(Quizzes.this));
                                recyclerView.setAdapter(quizAdapter);
                            }
                        }
                        else {
                            quizList.addAll(tempList);
                            quizAdapter = new QuizAdapter(Quizzes.this, quizList);
                            recyclerView.setLayoutManager(new LinearLayoutManager(Quizzes.this));
                            recyclerView.setAdapter(quizAdapter);
                        }
                    }
                    else{
                        quizList.addAll(tempList);
                        quizAdapter = new QuizAdapter(Quizzes.this,quizList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Quizzes.this));
                        recyclerView.setAdapter(quizAdapter);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}



















package com.example.android.quizbee.MainApp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.quizbee.R;
import com.example.android.quizbee.adapters.GradeAdapter;

public class Grades extends AppCompatActivity {

    private RecyclerView gradeView;
    private GradeAdapter gradeAdapter;
    private AttemptQuiz attemptQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);
        gradeView = findViewById(R.id.gradeViewTeacher);

        gradeAdapter = new GradeAdapter(Grades.this, AttemptQuiz.completedList);
        gradeView.setLayoutManager(new LinearLayoutManager(Grades.this));
        gradeView.setAdapter(gradeAdapter);
    }

}
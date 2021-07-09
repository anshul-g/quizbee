package com.example.android.quizbee.MainApp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.quizbee.R;

public class ResultActivity extends AppCompatActivity {

    private Grades grades;
    private TextView scoreText;
    private TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        scoreText = findViewById(R.id.scoreText);
        header = findViewById(R.id.header);
        setUpViews();
    }

    private void setUpViews() {
        String scoredata = getIntent().getStringExtra("Score");
        String fullscore = getIntent().getStringExtra("Size");

        scoreText.setText(scoredata+"/"+fullscore);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Quizzes.class);
        startActivity(intent);
    }
}
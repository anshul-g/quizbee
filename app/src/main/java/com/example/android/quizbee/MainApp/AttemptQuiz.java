package com.example.android.quizbee.MainApp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.quizbee.R;
import com.example.android.quizbee.adapters.OptionAdapter;
import com.example.android.quizbee.models.Question;
import com.example.android.quizbee.models.Quiz;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AttemptQuiz extends AppCompatActivity {

    // Declaring views and variables
    private Button btnNext;
    private Button btnPrevious;
    private Button btnSubmit;
    private TextView description;
    private TextView questionNum;
    private TextView quizTimer;
    private FirebaseFirestore database;
    private RecyclerView recyclerView;
    private OptionAdapter optionAdapter;
    private AlertDialog mAlertDialog;
    private CountDownTimer timer;

    public static ArrayList<Quiz> quizList = new ArrayList<>();
    public static ArrayList<Quiz> completedList = new ArrayList<>();
    public static ArrayList<String> size = new ArrayList<>();
    public static ArrayList<Integer> score = new ArrayList<>();
    private Map<String, Question> questionMap;
    private Question question;

    private String qindex;
    private int quizSize;
    public int index;
    public static int currentScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attempt_quiz);

        // Initialising views and variables
        questionMap = new HashMap<>();
        question = new Question();

        index = 1;
        currentScore = 0;

        btnNext = findViewById(R.id.nextBtn);
        btnPrevious = findViewById(R.id.previousBtn);
        btnSubmit = findViewById(R.id.btnSubmit);
        description = findViewById(R.id.description);
        questionNum = findViewById(R.id.questionNum);
        quizTimer = findViewById(R.id.timerText);
        recyclerView = findViewById(R.id.optionList);

        setupEventListener();

        // Retrieving data of quiz that the user clicks on
        database = FirebaseFirestore.getInstance();
        String quizID = getIntent().getStringExtra("id");

        database.collection("quizzes").whereEqualTo("id", quizID).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshot) {
                        quizList = (ArrayList<Quiz>) snapshot.toObjects(Quiz.class);
                        questionMap = quizList.get(0).getQuestions();
                        quizSize = questionMap.size();

                        int listSize = completedList.size();
                        if(listSize!=0 && completedList.get(listSize-1).getId().equals(quizList.get(quizList.size()-1).getId())){
                            Toast.makeText(AttemptQuiz.this,"Quiz Already Attempted",Toast.LENGTH_SHORT).show();
                            AttemptQuiz.this.finish();
                        } else {
                            bindViews();
                        }
                    }
                });
    }

    // Setting methods for button clicks
    private void setupEventListener() {

        // For next button
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                bindViews();
                if (timer!=null){
                    timer.cancel();
                }
            }
        });

        // For previous button
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index--;
                bindViews();
                if (timer!=null){
                    timer.cancel();
                }
            }
        });

        // For submit button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPopUp();
            }
        });
    }

    // Asking for confirmation;
    public void dialogPopUp() {
        AlertDialog.Builder submitAlert = new AlertDialog.Builder(AttemptQuiz.this,R.style.Theme_Quizbee);
        submitAlert.setTitle("Do you want to submit the quiz?");

        submitAlert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                calculateScore();
                submitQuiz();
                finish();
            }
        });

        submitAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        mAlertDialog = submitAlert.create();
        mAlertDialog.show();
        mAlertDialog.getWindow().setLayout(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    // Collecting quiz data;
    // and starting next Activity
    private void submitQuiz() {
        // Adding completed quiz to a new list
        completedList.add(quizList.get(0));
        size.add(String.valueOf(questionMap.size()));
        score.add(currentScore);

        // Setting up intent for starting Result Activity
        Intent intent = new Intent(AttemptQuiz.this, ResultActivity.class);
        intent.putExtra("Size", String.valueOf(questionMap.size()));
        intent.putExtra("Score", String.valueOf(currentScore));
        startActivity(intent);
    }

    // Calculating user score
    private void calculateScore() {
        for (int i = 1; i <= questionMap.size(); i++) {
            String quesNo = "question" + i;
            String tempUserAns = questionMap.get(quesNo).getUserAnswer();
            String tempAns = questionMap.get(quesNo).getAnswer();

            if (tempUserAns == null) {
                continue;
            }
            if (tempUserAns.equals(tempAns)) {
                currentScore++;
            }
        }
    }

    // Setting up visibility of buttons and RecyclerView
    private void bindViews() {
//        Log.d("timerdata","Quiz completed");

            btnNext.setVisibility(View.GONE);
            btnPrevious.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.GONE);

            if (index == 1 && quizSize == 1) {
                btnSubmit.setVisibility(View.VISIBLE);
            } else if (index == 1) {
                btnNext.setVisibility(View.VISIBLE);
            } else if (index == quizSize) {
                btnPrevious.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.VISIBLE);
            } else {
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
            }

            // Setting up timer
            int quizTime_ms = quizList.get(0).getTime()*60000;
            timer = new CountDownTimer(quizTime_ms,1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    @SuppressLint("DefaultLocale")
                    String setText = String.format("%02d:%02d:%02d",
                            (millisUntilFinished/(1000*3600))%60,
                            (millisUntilFinished/(1000*60))%60,
                            (millisUntilFinished/1000) % 60);
                    quizTimer.setText(setText);
                }

                @Override
                public void onFinish() {
                    int listSize = completedList.size();
                    if(listSize!=0 && completedList.get(listSize-1).getId().equals(quizList.get(0).getId())){
                        Log.d("timerdata","Quiz completed");
                    }
                    else {
                        calculateScore();
                        submitQuiz();
                        finish();
                    }
                }
            };
            timer.start();

            // Setting up questions and options view
            qindex = "question" + index;
            question = questionMap.get(qindex);

            description.setText(new StringBuilder().append(index).append(". ").append(question.getDescription()).toString());
            questionNum.setText(new StringBuilder().append(index).append("/").append(questionMap.size()));

            recyclerView.setLayoutManager(new LinearLayoutManager(AttemptQuiz.this));
            optionAdapter = new OptionAdapter(AttemptQuiz.this, question);
            optionAdapter.options.add(0, question.getOption1());
            optionAdapter.options.add(1, question.getOption2());
            optionAdapter.options.add(2, question.getOption3());
            optionAdapter.options.add(3, question.getOption4());

            recyclerView.setAdapter(optionAdapter);
    }

    @Override
    public void onBackPressed(){
    }
}

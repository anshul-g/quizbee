package com.example.android.quizbee.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.quizbee.MainApp.AttemptQuiz;
import com.example.android.quizbee.R;
import com.example.android.quizbee.models.Quiz;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class QuizAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<Quiz> quizList;
    AttemptQuiz attemptQuiz = new AttemptQuiz();

    public QuizAdapter(Context context,ArrayList<Quiz> quizList) {
        this.context = context;
        this.quizList = quizList;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_item, parent, false);
            ViewHolderClass viewHolderClass = new ViewHolderClass(view);
            return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass) holder;

        Quiz quizData = quizList.get(position);
        viewHolderClass.quizTitle.setText(quizData.getName());
        viewHolderClass.quizDate.setText(quizData.getDate());
        String timeText = "Time: "+String.valueOf(quizData.getTime())+" minutes";
        viewHolderClass.quizTime.setText(timeText);
        viewHolderClass.quizCourse.setText(quizData.getCourse());

        viewHolderClass.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolderClass.itemView.getContext(), AttemptQuiz.class);
                intent.putExtra("id", quizList.get(position).getId());
                viewHolderClass.itemView.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return quizList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView quizTitle,quizDate,quizTime,quizCourse;

        public ViewHolderClass(@NonNull @NotNull View itemView) {
            super(itemView);
            quizTitle = itemView.findViewById(R.id.quizTitle);
            quizDate = itemView.findViewById(R.id.quizDate);
            quizTime = itemView.findViewById(R.id.quizTime);
            quizCourse = itemView.findViewById(R.id.quizCourse);
        }
    }
}

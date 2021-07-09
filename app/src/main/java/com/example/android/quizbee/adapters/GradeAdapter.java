package com.example.android.quizbee.adapters;

import android.content.Context;
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

public class GradeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private AttemptQuiz attemptQuiz;
    private Context context;
    private ArrayList<Quiz> completedQuiz;

    public GradeAdapter(Context context,ArrayList<Quiz> completedQuiz) {
        this.context = context;
        this.completedQuiz = completedQuiz;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grade_item,parent,false);
        GradeHolder gradeHolder = new GradeHolder(view);
        return gradeHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        GradeHolder gradeHolder = (GradeHolder) holder;
        String toPrint = AttemptQuiz.score.get(position) + "/" + AttemptQuiz.size.get(position);
        Quiz quiz = completedQuiz.get(position);

        gradeHolder.quizScore.setText(toPrint);
        gradeHolder.quizName.setText(quiz.getName());
    }

    @Override
    public int getItemCount() {
        int count;
        if (completedQuiz==null){
            count = 0;
        }
        else{
            count = completedQuiz.size();
        }
        return count;
    }

    public class GradeHolder extends RecyclerView.ViewHolder {

        TextView quizName;
        TextView quizScore;

        public GradeHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            quizName = itemView.findViewById(R.id.quizName);
            quizScore = itemView.findViewById(R.id.quizScore);
        }
    }
}

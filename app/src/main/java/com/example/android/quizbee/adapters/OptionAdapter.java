package com.example.android.quizbee.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.quizbee.R;
import com.example.android.quizbee.models.Question;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Question question = new Question();
    public ArrayList<String> options = new ArrayList<String>();

    public OptionAdapter(Context context,  Question question) {
        this.question = question;
    }

    public String [] answers;

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_item,parent,false);
        OptionHolder optionHolder = new OptionHolder(view);
        optionHolder.setIsRecyclable(false);
        return optionHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        OptionHolder optionHolder = (OptionHolder) holder;
        optionHolder.option.setText(options.get(position));

        if (question.getUserAnswer() == options.get(position)){
            optionHolder.itemView.setBackgroundResource(R.drawable.quizbox_sel);
        }
        else{
            optionHolder.itemView.setBackgroundResource(R.drawable.quizbox_unsel);
        }

        optionHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.setUserAnswer(options.get(position));
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class OptionHolder extends RecyclerView.ViewHolder{

        private TextView option;
        public OptionHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            option = itemView.findViewById(R.id.optionItem);
        }
    }
}

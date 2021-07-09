package com.example.android.quizbee.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Quiz implements Serializable {
    String id;
    String Name;
    int time;
    String date;
    Map<String,Question> questions = new HashMap<>();
    String course;

    public Quiz() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Map<String,Question> questions) {
        this.questions = questions;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

}

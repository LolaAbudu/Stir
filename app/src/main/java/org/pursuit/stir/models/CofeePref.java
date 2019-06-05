package org.pursuit.stir.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class CofeePref {
    private String questionOneAnswer;
    private String questionTwoAnswer;

    public User user;

    public CofeePref() {
    }

    public CofeePref(String questionOneAnswer, String questionTwoAnswer) {

        this.questionOneAnswer = questionOneAnswer;
        this.questionTwoAnswer = questionTwoAnswer;
    }

    public String getQuestionOneAnswer() {
        return questionOneAnswer;
    }

    public void setQuestionOneAnswer(String questionOneAnswer) {
        this.questionOneAnswer = questionOneAnswer;
    }

    public String getQuestionTwoAnswer() {
        return questionTwoAnswer;
    }

    public void setQuestionTwoAnswer(String questionTwoAnswer) {
        this.questionTwoAnswer = questionTwoAnswer;
    }
}


package com.example.ahmed.projectlms.Models;

/**
 * Created by Mohab on 4/11/2017.
 */

public class QuizQuestionModel
{
    String questionTypeId, questionText, questionAnswer, questionType, quizQuestionId;

    public String getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(String questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuizQuestionId() {
        return quizQuestionId;
    }

    public void setQuizQuestionId(String quizQuestionId) {
        this.quizQuestionId = quizQuestionId;
    }
}

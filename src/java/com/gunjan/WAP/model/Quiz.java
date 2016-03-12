/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gunjan.WAP.model;

/**
 *
 * @author grsky
 */
public class Quiz {

    private Question[] questions = {new Question("1,2,3,4,5", "6", "Six"), new Question("6,5,4,3,2", "1", "One")};

    private int[] scoreVal = {10, 5, 2, 0};

    private int correctCount;

    private int currIndex;

    private int totScore = 0;

    public int getTotScore() {
        return totScore;
    }

    public Quiz() {
        correctCount = 0;
        currIndex = 0;
    }

    private int getScore() {
        try {
            return scoreVal[getCurrentTries()];
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }

    public int getNumCorrect() {
        return correctCount;
    }

    public String getCurrentQuestion() {
        return questions[currIndex].getQuestion();
    }

    public String getCurrentAns() {
        return questions[currIndex].getAnswer();
    }

    public String getCurrentHint() {
        return questions[currIndex].getHint();
    }

    public void increaseCurrTries() {
        questions[currIndex].increaseTries();
    }

    public int getCurrentTries() {
        return questions[currIndex].getTries();
    }

    public boolean isCorrect(String answer) {
        if (questions[currIndex].getAnswer().equals(answer)) {
            return true;

        }
        increaseCurrTries();
        return false;
    }

    public void scoreAnswer() {
        if (correctCount < questions.length && currIndex < questions.length) {
            correctCount++;
            totScore += getScore();
            currIndex++;
        }
    }

    public int getNumQuestions() {
        return questions.length;
    }

    public Object getCurrentQuestionIndex() {
        return currIndex;
    }

}

class Question {

    private String question;
    private String answer;
    private String hint;
    private int tries = 0;

    public int getTries() {
        return tries;
    }

    public void increaseTries() {
        tries++;
    }

    public Question(String question, String answer, String hint) {
        this.question = question;
        this.answer = answer;
        this.hint = hint;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getHint() {
        return hint;
    }

}

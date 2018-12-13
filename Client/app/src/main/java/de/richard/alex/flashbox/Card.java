package de.richard.alex.flashbox;

public class Card {
    private String question;
    private String[] answers;
    private int correct_answer;
    private String explanation;


    public Card(String question, String answer0, String answer1, String answer2, String explanation, int correct_answer) {
        this.question = question;
        this.answers = new String[3];
        this.answers[0] = answer0;
        this.answers[1] = answer1;
        this.answers[2] = answer2;
        this.correct_answer = correct_answer;
        this.explanation = explanation;

    }





    //Getters and Setters

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrect_answer() {
        return correct_answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public void setCorrect_answer(int correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}

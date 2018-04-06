/**
 * Author: Aimee Venturina
 * Created: 4/4/2018
 */
package com.mydemo;

/**
 * This is the question data transfer object which is used to pass
 * data from the controller to the service layer and vice-versa.
 */
public class QuestionDto {
    private String question;
    private Integer answer;

    public QuestionDto() {
        // Default constructor
    }

    public QuestionDto(String question, Integer answer) {
        this.answer = answer;
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }
}

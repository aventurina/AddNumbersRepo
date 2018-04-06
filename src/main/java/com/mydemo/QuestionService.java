/**
 * Author: Aimee Venturina
 * Created: 4/4/2018
 */
package com.mydemo;

import java.util.ArrayList;
import java.util.List;

public interface QuestionService {

    public int generateRandomNumber();

    public List<Integer> getNumbersToAdd();

    public boolean isAnswerCorrect(QuestionDto questionDto);

    public List<Integer> parseNumbersFromQuestion(String question);

    public void addToCorrectlyAnsweredQuestions(String question);

    public ArrayList<List<Integer>> getCorrectlyAnsweredQuestions();

    public boolean isQuestionADuplicate(String question);
}

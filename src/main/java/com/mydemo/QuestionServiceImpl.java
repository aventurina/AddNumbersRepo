/**
 * Author: Aimee Venturina
 * Created: 4/4/2018
 */
package com.mydemo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service(value = "questionService")
public class QuestionServiceImpl implements QuestionService {

    // The list of all the correctly answered questions
    private ArrayList<List<Integer>> correctlyAnsweredQuestions = new ArrayList<List<Integer>>();

    /**
     * Generates and returns a random integer between 1 to 10
     *
     * @return an int value
     */
    public int generateRandomNumber() {
        Random randomObj = new Random();
        return randomObj.ints(1, 10).findFirst().getAsInt();
    }

    /**
     * Returns a list of generated integer numbers
     *
     * @return an array of int values
     */
    public List<Integer> getNumbersToAdd() {
        int listSize = 3;
        ArrayList<Integer> numbers = new ArrayList<Integer>();

        for (int i = 0; i < listSize; i++) {
            numbers.add(generateRandomNumber());
        }

        return numbers;
    }

    /**
     * Checks if the answer to the question is correct by parsing the
     * question string and retrieving the list of numbers from the question.
     * Then the list of numbers are added and returns true if the answer
     * matches the sum of the parsed numbers from the question.
     * Otherwise it returns false.
     *
     * @param questionDto
     * @return true or false
     */
    public boolean isAnswerCorrect(QuestionDto questionDto) {
        List<Integer> numbers = parseNumbersFromQuestion(questionDto.getQuestion());
        int total = 0;

        for (int i = 0; i < numbers.size(); i++) {
            total += numbers.get(i);
        }

        if (total == questionDto.getAnswer()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Parses the list of numbers from the question string and
     * returns them as a list.
     *
     * @param question
     * @return a list of numbers
     */
    public List<Integer> parseNumbersFromQuestion(String question) {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        String numbersStr = question.substring(question.lastIndexOf(" "));
        String[] n = numbersStr.split(",");

        for (int i = 0; i < n.length; i++) {
            numbers.add(Integer.parseInt(n[i].trim()));
        }

        return numbers;
    }

    /**
     * Adds the numbers from the question to the list of previously answered
     * questions that were correct. The numbers are sorted in ascending order
     * first before they are added to the list.
     *
     * @param question
     */
    public void addToCorrectlyAnsweredQuestions(String question) {
        List<Integer> numbersFromThisQuestion = parseNumbersFromQuestion(question);
        Collections.sort(numbersFromThisQuestion);
        correctlyAnsweredQuestions.add(numbersFromThisQuestion);
    }

    /**
     * Checks if the question is valid by comparing against the list of numbers that
     * have already been answered correctly before. The question is first parsed by
     * retrieving just the numbers, then sorting them in ascending order, and then
     * lastly comparing them to the list of correctly answered questions.
     *
     * @param question
     * @return
     */
    public boolean isQuestionADuplicate(String question) {
        List<Integer> numbersFromThisQuestion = parseNumbersFromQuestion(question);
        boolean isDupe = false;
        int numCounter = 0;

        // Sort the parsed numbers first
        Collections.sort(numbersFromThisQuestion);

        // Then go through each correctly answered questions and compare
        // to the current question to see if this has been answered already
        for (int i = 0; i < correctlyAnsweredQuestions.size(); i++) {
            numCounter = 0;
            List<Integer> numsAlreadyAnswered = correctlyAnsweredQuestions.get(i);

            for (int j = 0; j < numsAlreadyAnswered.size(); j++) {
                if (numsAlreadyAnswered.get(j) == numbersFromThisQuestion.get(j)) {
                    numCounter++;
                }
            }

            // If counter is 3, then that means these numbers have already
            // been answered correctly before, so it's a duplicate question.
            if (numCounter == 3) {
                isDupe = true;
                break;
            }
        }

        return isDupe;
    }

    /**
     * Gets the list of the numbers in the correctlyAnsweredQuestions array
     * @return
     */
    public ArrayList<List<Integer>> getCorrectlyAnsweredQuestions() {
        return correctlyAnsweredQuestions;
    }
}

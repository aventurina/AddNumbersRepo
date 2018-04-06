/**
 * Author: Aimee Venturina
 * Created: 4/4/2018
 */
package com.mydemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QuestionServiceImplTests {
    @Autowired
    private QuestionService questionService;

    @Test
    public void testGenerateRandomNumber() throws Exception {
        // Check that the random number is between 1 to 10
        int randomNum = questionService.generateRandomNumber();
        int min = 1;
        int max = 10;

        assertTrue("Error: random number " + randomNum + " is lower than min", randomNum >= min);
        assertTrue("Error: random number " + randomNum + " is higher than max",  randomNum  <= max);
    }

    @Test
    public void testGetNumbersToAdd() throws Exception {
        List<Integer> numbersToAdd = questionService.getNumbersToAdd();

        // List must not be empty
        assertTrue("Error: The list is empty", numbersToAdd != null);

        // List size should be 3
        assertTrue("Error: There are not enough numbers to add", numbersToAdd.size() == 3);
    }

    @Test
    public void testIsAnswerCorrect() throws Exception {
        QuestionDto questionDto = new QuestionDto("Please sum the numbers 4,5,6", 15);

        assertTrue("Error: The answer is incorrect", questionService.isAnswerCorrect(questionDto));
    }

    @Test
    public void testParseNumbersFromQuestion() throws Exception {
        List<Integer> numbersToAdd = questionService.parseNumbersFromQuestion("Please sum the numbers 7,8,9");

        // List must not be empty
        assertTrue("Error: The list is empty", numbersToAdd != null);

        // List size should be 3
        assertTrue("Error: There are not enough numbers to add", numbersToAdd.size() == 3);
    }

    @Test
    public void testAddToCorrectlyAnsweredQuestions() throws Exception {
        questionService.addToCorrectlyAnsweredQuestions("Please sum the numbers 3,1,5");

        ArrayList<Integer> numbersToAdd = new ArrayList<Integer>();
        numbersToAdd.add(3);
        numbersToAdd.add(1);
        numbersToAdd.add(5);
        Collections.sort(numbersToAdd);

        ArrayList<List<Integer>> correctlyAnsweredQuestions = questionService.getCorrectlyAnsweredQuestions();

        // numbersToAdd list must be equal to correctlyAnsweredQuestions list
        assertTrue("Error: Numbers are not added to the correctlyAnsweredQuestions list", numbersToAdd.equals(correctlyAnsweredQuestions.get(0)));
    }

    @Test
    public void testIsQuestionADuplicate() throws Exception {
        String newQuestion = "Please sum the numbers 9,3,7";
        questionService.addToCorrectlyAnsweredQuestions("Please sum the numbers 2,4,6");

        assertTrue("Error: The numbers in the question '" + newQuestion + "' already exist in the previously answered list: " +
        questionService.getCorrectlyAnsweredQuestions(), !questionService.isQuestionADuplicate(newQuestion));
    }
}

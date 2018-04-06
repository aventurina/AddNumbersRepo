/**
 * Author: Aimee Venturina
 * Created: 4/4/2018
 */
package com.mydemo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class contains the API endpoints for requesting a question
 * as well as sending the answer and the original question in order
 * to be checked and validated.
 */
@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * This is the API endpoint for requesting a question for adding numbers
     *
     * @return the initial question
     */
    @RequestMapping(value = "/question", method = RequestMethod.GET)
    public ResponseEntity<String> getQuestion() {
        try {
            String template = "Please sum the numbers %s";
            List<Integer> numbersToAdd = questionService.getNumbersToAdd();

            return new ResponseEntity<String>(String.format(template, StringUtils.join(numbersToAdd, ",")), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This is the API endpoint for checking the answer to the question
     * that was previously sent to the client. It also checks for duplicate
     * request. If the question has already been previously answered correctly,
     * then the client is informed of that and is asked to try again.
     *
     * @return a confirmation of whether the answer is correct or incorrect,
     * or the question has already been previously answered correctly
     */
    @RequestMapping(value = "/answer", method = RequestMethod.POST)
    public ResponseEntity<String> sendAnswer(@RequestBody QuestionDto questionDto) {
        try {
            String q = questionDto.getQuestion();
            String result = "Question: " + q + "\n" +
                "Your answer: " + questionDto.getAnswer() + "\n" +
                "Result: Your answer is %s";

            // Checks if the question has already been previously answered correctly.
            // If it has been previously answered correctly, then inform user
            // to retrieve a new question. This prevents cheating.
            if (questionService.isQuestionADuplicate(q)) {
                return new ResponseEntity<String>("The question has already been previously answered correctly.\n" +
                        "Please retrieve a new question to answer.", HttpStatus.BAD_REQUEST);
            }

            if (questionService.isAnswerCorrect(questionDto)) {
                // If answer is correct, the numbers from the question are added to the list of
                // already correctly answered questions for duplicate validation purposes later on
                questionService.addToCorrectlyAnsweredQuestions(q);

                return new ResponseEntity<String>(String.format(result, "correct!"), HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(String.format(result, "incorrect. Please try again."), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>("Invalid request. Please try again.", HttpStatus.BAD_REQUEST);
        }
    }
}

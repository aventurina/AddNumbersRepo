/**
 * Author: Aimee Venturina
 * Created: 4/4/2018
 */
package com.mydemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.Charset;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QuestionControllerTests {
    @Autowired
    private MockMvc mockMvc;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private HttpMessageConverter mappingJackson2HttpMessageConverter;


    @Test
    public void initialQuestion() throws Exception {
        this.mockMvc.perform(get("/question"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(",")))
                .andExpect(content().string(containsString("Please sum the numbers")));
    }

    @Test
    public void checkQuestionAndCorrectAnswer() throws Exception {
        QuestionDto questionDto = new QuestionDto(
                "Please sum the numbers 1,2,3", 6);

        String result = "Question: " + questionDto.getQuestion() + "\n" +
                "Your answer: " + questionDto.getAnswer() + "\n" +
                "Result: Your answer is %s";

        mockMvc.perform(MockMvcRequestBuilders.post("/answer")
                .content(asJsonString(questionDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(String.format(result, "correct!"))));
    }

    @Test
    public void checkQuestionAndIncorrectAnswer() throws Exception {
        QuestionDto questionDto = new QuestionDto(
                "Please sum the numbers 1,2,3", 10);

        String result = "Question: " + questionDto.getQuestion() + "\n" +
                "Your answer: " + questionDto.getAnswer() + "\n" +
                "Result: Your answer is %s";

        mockMvc.perform(MockMvcRequestBuilders.post("/answer")
                .content(asJsonString(questionDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(String.format(result, "incorrect. Please try again."))));
    }

    /**
     * Converts an object to a JSON string
     * @param obj
     * @return
     */
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

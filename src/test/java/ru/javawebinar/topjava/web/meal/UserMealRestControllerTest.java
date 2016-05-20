package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.MEAL1;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


/**
 * Created by propant on 20.05.2016.
 */
public class UserMealRestControllerTest extends AbstractControllerTest {

    public static final String REST_URL = UserMealRestController.REST_URL + '/';

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get(REST_URL).contentType(MediaType.APPLICATION_JSON)).
                andDo(print());

    }
}

package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static ru.javawebinar.topjava.MealTestData.MEAL1;
import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;
import static ru.javawebinar.topjava.MealTestData.USER_MEALS;
import static ru.javawebinar.topjava.MealTestData.MATCHER;



/**
 * Created by propant on 20.05.2016.
 */
public class UserMealsRestControllerTest extends AbstractControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;

    public static final String REST_URL = UserMealsRestController.REST_URL + '/';

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get(REST_URL).contentType(MediaType.APPLICATION_JSON)).
                andDo(print());

    }

    @Test
    public void testGet() throws Exception {

        mockMvc.perform(get(REST_URL + MEAL1.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get(REST_URL).contentType(MediaType.APPLICATION_JSON)).
                andDo(print());

    }

    @Test
    public void testUpdate() throws Exception {

        UserMeal updated = new UserMeal();
        updated.setId(MEAL1.getId());
        updated.setDescription("Updated MEAL 1");
        updated.setCalories(MEAL1.getCalories());
        updated.setDateTime(MEAL1.getDateTime());

        mockMvc.perform(put(REST_URL + MEAL1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        mockMvc.perform(get(REST_URL + MEAL1_ID).contentType(MediaType.APPLICATION_JSON)).
                andDo(print());

        /*MATCHER.assertEquals(updated, userService.get(USER_ID));

        mockMvc.perform(put(REST_URL + ))*/
    }

    @Test
    public void testCreate() throws Exception {

        UserMeal created = new UserMeal();

        created.setDescription("Created MEAL 25");
        created.setCalories(1500);
        created.setDateTime(LocalDateTime.now());

        ResultActions action = mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated());

        UserMeal returned = MATCHER.fromJsonAction(action);

        mockMvc.perform(get(REST_URL + returned.getId()).contentType(MediaType.APPLICATION_JSON)).
                andDo(print());

        /*MATCHER.assertEquals(returned, userMealService.get(returned.getId(), ));*/

    }

}

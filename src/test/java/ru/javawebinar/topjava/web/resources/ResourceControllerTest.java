package ru.javawebinar.topjava.web.resources;

import org.junit.Test;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by propant on 20.05.2016.
 */
public class ResourceControllerTest extends AbstractControllerTest {

    @Test
    public void stylesTest() throws Exception{
        mockMvc.perform(get("/resources/css/style.css")).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(content().contentType("text/css"));
    }
}
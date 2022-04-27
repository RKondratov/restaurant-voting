package ru.graduation.restaurantvoting.web.common;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.restaurantvoting.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.restaurantvoting.web.TestData.*;

class CommonControllerTest extends AbstractControllerTest {

    public static final String REST_URL = CommonController.REST_URL + "/";

    @Test
    @WithMockUser(roles = USER)
    void getMeals() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "meals/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(MEAL_FIRST, MEAL_SECOND, MEAL_THIRD));
    }

    @Test
    @WithMockUser(roles = ADMIN)
    void getMealsByRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "meals/" + RESTAURANT_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(MEAL_FIRST, MEAL_SECOND));
    }

    @Test
    @WithMockUser(roles = USER)
    void getVotingResult() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "votes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VOTE_FIRST, VOTE_SECOND));;

    }
}
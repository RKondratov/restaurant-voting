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
    void getTodayDishes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "dishes/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(DISH_FIRST, DISH_SECOND, DISH_THIRD));
    }

    @Test
    @WithMockUser(roles = ADMIN)
    void getTodayDishesByRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "dishes/restaurants/" + ONE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(DISH_FIRST, DISH_SECOND));
    }

    @Test
    @WithMockUser(roles = USER)
    void getVotingResult() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "votes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_RESULT_TO_MATCHER.contentJson(VOTE_FIRST, VOTE_SECOND));
    }
}
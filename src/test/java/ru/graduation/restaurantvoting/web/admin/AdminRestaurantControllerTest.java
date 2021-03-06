package ru.graduation.restaurantvoting.web.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.restaurantvoting.model.Restaurant;
import ru.graduation.restaurantvoting.repository.DishRepository;
import ru.graduation.restaurantvoting.repository.RestaurantRepository;
import ru.graduation.restaurantvoting.to.RestaurantTo;
import ru.graduation.restaurantvoting.util.JsonUtil;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.restaurantvoting.util.RestaurantUtil.createRestaurantFromTo;
import static ru.graduation.restaurantvoting.web.TestData.*;

class AdminRestaurantControllerTest extends AdminDishControllerTest {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private DishRepository dishRepository;
    private static final String REST_URL = AdminRestaurantController.REST_URL + "/";

    @Test
    @WithMockUser(roles = ADMIN)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANT_FIRST, RESTAURANT_SECOND, RESTAURANT_THIRD));
    }

    @Test
    @WithMockUser(roles = ADMIN)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANT_FIRST));
    }

    @Test
    @WithMockUser(roles = ADMIN)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = USER)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    void create() throws Exception {
        RestaurantTo restaurantTo = new RestaurantTo(null, "newRes");
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurantTo)))
                .andExpect(status().isCreated());

        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        RESTAURANT_MATCHER.assertMatch(created, restaurantRepository.getById(created.id()));
    }

    @Test
    @WithMockUser(roles = ADMIN)
    void update() throws Exception {
        RestaurantTo update = new RestaurantTo(ONE, "Update");
        perform(MockMvcRequestBuilders.put(REST_URL + ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(update)))
                .andDo(print())
                .andExpect(status().isNoContent());
        RESTAURANT_MATCHER.assertMatch(restaurantRepository.getById(ONE), createRestaurantFromTo(update));
    }

    @Test
    @WithMockUser(roles = ADMIN)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + ONE))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(restaurantRepository.findById(ONE).isPresent());
        assertTrue(dishRepository.findAllByRestaurantIdAndCreationDate(ONE, new Date(), new Date()).isEmpty());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND_ID))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(roles = USER)
    void deleteForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + USER_ID))
                .andExpect(status().isForbidden());
    }
}
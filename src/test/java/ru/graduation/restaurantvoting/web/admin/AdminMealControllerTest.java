package ru.graduation.restaurantvoting.web.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.restaurantvoting.model.Meal;
import ru.graduation.restaurantvoting.model.Restaurant;
import ru.graduation.restaurantvoting.repository.MealRepository;
import ru.graduation.restaurantvoting.repository.RestaurantRepository;
import ru.graduation.restaurantvoting.util.JsonUtil;
import ru.graduation.restaurantvoting.web.AbstractControllerTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.restaurantvoting.web.TestData.*;

class AdminMealControllerTest extends AbstractControllerTest {
    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    private static final String REST_URL = AdminMealController.REST_URL + "/";

    @Test
    @WithMockUser(roles = ADMIN)
    void create() throws Exception {
        Restaurant restaurant = restaurantRepository.getById(RESTAURANT_ID);
        Meal meal = new Meal("newMeal", 100, new Date(), restaurant);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(meal)))
                .andExpect(status().isCreated());
        Meal created = MEAL_MATCHER.readFromJson(action);
        MEAL_MATCHER.assertMatch(created, mealRepository.getById(created.id()));
    }

    @Test
    @WithMockUser(roles = ADMIN)
    void update() throws Exception {
        Meal update = new Meal("Update", 100, new Date(), restaurantRepository.getById(RESTAURANT_ID));
        perform(MockMvcRequestBuilders.put(REST_URL + MEAL_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(update)))
                .andDo(print())
                .andExpect(status().isNoContent());
        MEAL_MATCHER.assertMatch(mealRepository.getById(MEAL_ID), update);
    }

    @Test
    @WithMockUser(roles = ADMIN)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MEAL_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(mealRepository.findById(MEAL_ID).isPresent());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND))
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
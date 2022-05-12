package ru.graduation.restaurantvoting.web.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.restaurantvoting.model.Dish;
import ru.graduation.restaurantvoting.repository.DishRepository;
import ru.graduation.restaurantvoting.to.DishTo;
import ru.graduation.restaurantvoting.util.JsonUtil;
import ru.graduation.restaurantvoting.web.AbstractControllerTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.restaurantvoting.util.DishUtil.createDishFromTo;
import static ru.graduation.restaurantvoting.web.TestData.*;

class AdminDishControllerTest extends AbstractControllerTest {
    @Autowired
    private DishRepository dishRepository;
    private static final String REST_URL = AdminDishController.REST_URL + "/";

    @Test
    @WithMockUser(roles = ADMIN)
    void create() throws Exception {
        DishTo dishTo = new DishTo(null, "newDish", 100, new Date(), ONE);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dishTo)))
                .andExpect(status().isCreated());
        Dish created = DISH_MATCHER.readFromJson(action);
        DISH_MATCHER.assertMatch(created, dishRepository.getById(created.id()));
    }

    @Test
    @WithMockUser(roles = ADMIN)
    void update() throws Exception {
        DishTo update = new DishTo(ONE, "Update", 100, new Date(), ONE);
        perform(MockMvcRequestBuilders.put(REST_URL + ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(update)))
                .andDo(print())
                .andExpect(status().isNoContent());
        DISH_MATCHER.assertMatch(dishRepository.getById(ONE), createDishFromTo(update));
    }

    @Test
    @WithMockUser(roles = ADMIN)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + ONE))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(dishRepository.findById(ONE).isPresent());
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
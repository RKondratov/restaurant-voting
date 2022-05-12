package ru.graduation.restaurantvoting.web.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.restaurantvoting.model.User;
import ru.graduation.restaurantvoting.repository.UserRepository;
import ru.graduation.restaurantvoting.to.UserTo;
import ru.graduation.restaurantvoting.util.JsonUtil;
import ru.graduation.restaurantvoting.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.restaurantvoting.web.TestData.*;

class AdminUserControllerTest extends AbstractControllerTest {
    @Autowired
    private UserRepository userRepository;
    public static final String REST_URL = AdminUserController.REST_URL + "/";

    @Test
    @WithMockUser(roles = ADMIN)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(USER_ADMIN_AND_USER, USER_USER, USER_ADMIN));
    }

    @Test
    @WithMockUser(roles = ADMIN)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + ADMIN_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(USER_ADMIN));
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
        UserTo userTo = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(userTo)))
                .andExpect(status().isCreated());
        User created = USER_MATCHER.readFromJson(action);
        USER_MATCHER.assertMatch(created, userRepository.getById(created.id()));
    }

    @Test
    @WithMockUser(roles = ADMIN)
    void update() throws Exception {
        UserTo userTo = getForUpdate();
        perform(MockMvcRequestBuilders.put(REST_URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(userTo, "newPass")))
                .andDo(print())
                .andExpect(status().isNoContent());
        USER_MATCHER.assertMatch(userRepository.getById(USER_ID), getUpdated(userTo));
    }

    @Test
    @WithMockUser(roles = ADMIN)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + USER_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(userRepository.findById(USER_ID).isPresent());
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
package ru.graduation.restaurantvoting.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.restaurantvoting.model.VotingResult;
import ru.graduation.restaurantvoting.repository.RestaurantRepository;
import ru.graduation.restaurantvoting.repository.VotingResultRepository;
import ru.graduation.restaurantvoting.to.VoteTo;
import ru.graduation.restaurantvoting.util.JsonUtil;
import ru.graduation.restaurantvoting.web.AbstractControllerTest;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.restaurantvoting.web.TestData.*;

class UserControllerTest extends AbstractControllerTest {
    @Autowired
    protected VotingResultRepository votingResultRepository;
    @Autowired
    protected RestaurantRepository restaurantRepository;
    public static final String REST_URL = UserController.REST_URL + "/";

    @Test
    @WithMockUser(roles = USER)
    void create() throws Exception {
        VoteTo vote = new VoteTo(LocalDate.now().atStartOfDay(),
                restaurantRepository.getById(RESTAURANT_ID), USER_ID);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(vote)))
                .andExpect(status().isCreated());
        VotingResult created = VOTE_MATCHER.readFromJson(action);
        VOTE_MATCHER.assertMatch(created, votingResultRepository.getById(created.id()));
    }

    @Test
    @WithMockUser(roles = ADMIN)
    void createForbidden() throws Exception {
        VoteTo vote = new VoteTo(LocalDate.now().atStartOfDay(),
                restaurantRepository.getById(RESTAURANT_ID), USER_ID);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(vote)))
                .andExpect(status().isForbidden());
    }
}
package ru.graduation.restaurantvoting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.restaurantvoting.TestUtil;
import ru.graduation.restaurantvoting.model.Vote;
import ru.graduation.restaurantvoting.repository.RestaurantRepository;
import ru.graduation.restaurantvoting.repository.VoteRepository;
import ru.graduation.restaurantvoting.to.VoteTo;
import ru.graduation.restaurantvoting.util.JsonUtil;
import ru.graduation.restaurantvoting.web.AbstractControllerTest;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.ZoneOffset.UTC;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.restaurantvoting.web.TestData.*;

class VoteControllerTest extends AbstractControllerTest {
    @Autowired
    protected VoteRepository voteRepository;
    @Autowired
    protected RestaurantRepository restaurantRepository;
    @Autowired
    protected VoteController voteController;

    private static final String REST_URL = VoteController.REST_URL + "/";
    private static final String TIME_BEFORE_VOTE_END = "2022-04-01T10:00:00Z";
    private static final String TIME_AFTER_VOTE_END = "2022-04-01T12:00:00Z";

    @Test
    @WithMockUser(roles = USER)
    void createBeforeVoteEnd() throws Exception {
        ReflectionTestUtils.setField(voteController, "clock", Clock.fixed(Instant.parse(TIME_BEFORE_VOTE_END), UTC));
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + ONE)
                .contentType(MediaType.APPLICATION_JSON).with(TestUtil.userHttpBasic(USER_USER)))
                .andExpect(status().isCreated());
        VoteTo created = VOTE_TO_MATCHER.readFromJson(action);
        VOTE_TO_MATCHER.assertMatch(created, VOTE_TO_FIRST);
    }

    @Test
    @WithMockUser(roles = USER)
    void createFirstTimeAfterVoteEnd() throws Exception {
        ReflectionTestUtils.setField(voteController, "clock", Clock.fixed(Instant.parse(TIME_AFTER_VOTE_END), UTC));
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + ONE)
                .contentType(MediaType.APPLICATION_JSON).with(TestUtil.userHttpBasic(USER_USER)))
                .andExpect(status().isCreated());
        VoteTo created = VOTE_TO_MATCHER.readFromJson(action);
        VOTE_TO_MATCHER.assertMatch(created, VOTE_TO_FIRST);
    }

    @Test
    @WithMockUser(roles = USER)
    void createAfterTime() throws Exception {
        voteRepository.save(new Vote(LocalDateTime.ofInstant(Instant.parse(TIME_BEFORE_VOTE_END), UTC), ONE, USER_ID));
        ReflectionTestUtils.setField(voteController, "clock", Clock.fixed(Instant.parse(TIME_AFTER_VOTE_END), UTC));
        perform(MockMvcRequestBuilders.post(REST_URL + ONE)
                .contentType(MediaType.APPLICATION_JSON).with(TestUtil.userHttpBasic(USER_USER)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    void createForbidden() throws Exception {
        VoteTo vote = new VoteTo(LocalDate.now().atStartOfDay(), ONE, USER_ID);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(vote)))
                .andExpect(status().isForbidden());
    }
}
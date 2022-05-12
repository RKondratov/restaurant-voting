package ru.graduation.restaurantvoting.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.restaurantvoting.exception.IllegalRequestDataException;
import ru.graduation.restaurantvoting.model.Vote;
import ru.graduation.restaurantvoting.repository.RestaurantRepository;
import ru.graduation.restaurantvoting.repository.UserRepository;
import ru.graduation.restaurantvoting.repository.VoteRepository;
import ru.graduation.restaurantvoting.to.VoteTo;
import ru.graduation.restaurantvoting.web.AuthUser;

import java.net.URI;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static ru.graduation.restaurantvoting.util.VoteUtil.createVoteFromTo;
import static ru.graduation.restaurantvoting.util.validation.ValidationUtil.checkVotingTime;

@RestController
@RequestMapping(value = VoteController.REST_URL)
@Slf4j
public class VoteController {
    @Autowired
    protected VoteRepository voteRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected RestaurantRepository restaurantRepository;

    static final String REST_URL = "/api/votes";
    private final Clock clock = Clock.systemDefaultZone();

    @PostMapping(value = "/{restaurantId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<VoteTo> create(@PathVariable Integer restaurantId, @AuthenticationPrincipal AuthUser user) {
        if (restaurantId == null || !restaurantRepository.existsById(restaurantId)) {
            throw new IllegalRequestDataException("Restaurant must exist and restaurantId is not null");
        }
        final VoteTo voteTo = new VoteTo(LocalDateTime.now(), restaurantId, user.id());
        final Integer voteNum = voteRepository.countTodayVotesByUserID(user.id(),
                LocalDate.now(clock).atStartOfDay(), LocalDate.now(clock).atTime(LocalTime.MAX));
        if (voteNum > 0 && checkVotingTime(LocalTime.now(clock))) {
            return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(voteTo);
        }
        log.info("create {}", voteTo);
        final Vote created = voteRepository.save(createVoteFromTo(voteTo));
        voteTo.setId(created.getId());
        final URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}").buildAndExpand(voteTo.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(voteTo);
    }
}
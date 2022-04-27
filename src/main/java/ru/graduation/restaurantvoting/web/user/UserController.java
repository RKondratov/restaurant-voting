package ru.graduation.restaurantvoting.web.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.restaurantvoting.model.VotingResult;
import ru.graduation.restaurantvoting.repository.VotingResultRepository;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static ru.graduation.restaurantvoting.util.validation.ValidationUtil.*;

@RestController
@RequestMapping(value = UserController.REST_URL)
@Slf4j
public class UserController {
    @Autowired
    protected VotingResultRepository votingResultRepository;

    static final String REST_URL = "/api/user";

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VotingResult> create(@Valid @RequestBody VotingResult votingResult) {
        checkNew(votingResult);
        checkVote(votingResult);
        if (checkVotingDate(votingResult)) {
            return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(votingResult);
        }
        log.info("create {}", votingResult);
        final VotingResult created = votingResultRepository.save(votingResult);
        final URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}

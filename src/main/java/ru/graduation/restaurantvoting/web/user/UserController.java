package ru.graduation.restaurantvoting.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import java.util.Calendar;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = UserController.REST_URL)
public class UserController {
    @Autowired
    protected VotingResultRepository votingResultRepository;

    static final String REST_URL = "/api/user";

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VotingResult> create(@Valid @RequestBody VotingResult votingResult) {
        Calendar date = Calendar.getInstance();
        date.setTime(votingResult.getRegistered());
        if (date.get(Calendar.HOUR_OF_DAY) >= 11) {
            return ResponseEntity.status(FORBIDDEN).body(votingResult);
        }
        VotingResult created = votingResultRepository.save(votingResult);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}

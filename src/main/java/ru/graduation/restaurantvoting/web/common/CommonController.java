package ru.graduation.restaurantvoting.web.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.graduation.restaurantvoting.model.Dish;
import ru.graduation.restaurantvoting.model.VotingResult;
import ru.graduation.restaurantvoting.repository.DishRepository;
import ru.graduation.restaurantvoting.repository.VotingResultRepository;
import ru.graduation.restaurantvoting.to.VotingResultTo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@RestController
@RequestMapping(value = CommonController.REST_URL)
@Slf4j
public class CommonController {
    @Autowired
    protected DishRepository dishRepository;
    @Autowired
    protected VotingResultRepository votingResultRepository;

    static final String REST_URL = "/api/common";

    @GetMapping("/dishes")
    public List<Dish> getDishes() {
        log.info("getDishes");
        return dishRepository.findAll();
    }

    @GetMapping("/dishes/{restaurantId}")
    public List<Dish> getDishesByRestaurant(@PathVariable int restaurantId) {
        log.info("getDishesByRestaurant with id = {}", restaurantId);
        return dishRepository.findAllByRestaurantId(restaurantId);
    }

    @GetMapping("/votes")
    public List<VotingResultTo> getVotingResult() {
        log.info("getVotingResult");
        final List<VotingResult> list = new ArrayList<>();
        final List<VotingResultTo> voteList = new ArrayList<>();
        votingResultRepository.findAllTodayVotes(LocalDate.now().atStartOfDay()).stream().collect(groupingBy(VotingResult::getUser))
                .forEach((user, votingResults) -> {
                    votingResults.sort((VotingResult v1, VotingResult v2) ->
                            v2.getRegistered().compareTo(v1.getRegistered()));
                    votingResults.stream().findFirst().ifPresent(list::add);
                });
        list.stream().collect(groupingBy(VotingResult::getRestaurant, counting()))
                .forEach((k, v) -> voteList.add(new VotingResultTo(k, v)));
        return voteList;
    }
}

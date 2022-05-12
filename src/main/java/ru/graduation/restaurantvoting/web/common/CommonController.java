package ru.graduation.restaurantvoting.web.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.graduation.restaurantvoting.model.Dish;
import ru.graduation.restaurantvoting.model.Vote;
import ru.graduation.restaurantvoting.repository.DishRepository;
import ru.graduation.restaurantvoting.repository.VoteRepository;
import ru.graduation.restaurantvoting.to.VotingResultTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static ru.graduation.restaurantvoting.util.DateUtil.getTodayStartDate;
import static ru.graduation.restaurantvoting.util.DateUtil.getTomorrowStartDate;

@RestController
@RequestMapping(value = CommonController.REST_URL)
@Slf4j
public class CommonController {
    @Autowired
    protected DishRepository dishRepository;
    @Autowired
    protected VoteRepository voteRepository;

    static final String REST_URL = "/api/common";

    @GetMapping("/dishes")
    public List<Dish> getTodayDishes() {
        log.info("getTodayDishes");
        return dishRepository.findAllByCreationDate(getTodayStartDate(), getTomorrowStartDate());
    }

    @GetMapping("/dishes/restaurants/{id}")
    public List<Dish> getTodayDishesByRestaurant(@PathVariable int id) {
        log.info("getTodayDishesByRestaurant with restaurant id = {}", id);
        return dishRepository.findAllByRestaurantIdAndCreationDate(id, getTodayStartDate(), getTomorrowStartDate());
    }

    @GetMapping("/votes")
    public List<VotingResultTo> getVotingResult() {
        log.info("getVotingResult");
        final List<Vote> list = new ArrayList<>();
        final List<VotingResultTo> voteList = new ArrayList<>();
        voteRepository.findAllTodayVotes(LocalDate.now().atStartOfDay(), LocalDate.now().atTime(LocalTime.MAX))
                .stream().collect(groupingBy(Vote::getUserId))
                .forEach((user, votingResults) -> {
                    votingResults.sort((Vote v1, Vote v2) -> v2.getRegistered().compareTo(v1.getRegistered()));
                    votingResults.stream().findFirst().ifPresent(list::add);
                });
        list.stream().collect(groupingBy(Vote::getRestaurantId, counting()))
                .forEach((k, v) -> voteList.add(new VotingResultTo(k, v)));
        return voteList;
    }
}
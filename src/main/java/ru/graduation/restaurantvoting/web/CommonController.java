package ru.graduation.restaurantvoting.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.graduation.restaurantvoting.model.Meal;
import ru.graduation.restaurantvoting.model.Restaurant;
import ru.graduation.restaurantvoting.model.VotingResult;
import ru.graduation.restaurantvoting.repository.MealRepository;
import ru.graduation.restaurantvoting.repository.VotingResultRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@RestController
@RequestMapping(value = CommonController.REST_URL)
public class CommonController {
    @Autowired
    protected MealRepository mealRepository;
    @Autowired
    protected VotingResultRepository votingResultRepository;

    static final String REST_URL = "/api/common";

    @GetMapping("/meals")
    public List<Meal> getMeals() {
        return mealRepository.findAll();
    }

    @GetMapping("/meals/{restaurantId}")
    public List<Meal> getMealsByRestaurant(@PathVariable int restaurantId) {
        return mealRepository.findAllByRestaurantId(restaurantId);
    }

    @GetMapping("/votes")
    public Map<Restaurant, Long> getVotingResult() {
        final List<VotingResult> list = new ArrayList<>();
        votingResultRepository.findAll().stream().collect(groupingBy(VotingResult::getUser))
                .forEach((user, votingResults) -> {
                    votingResults.sort((VotingResult v1, VotingResult v2) ->
                            v2.getRegistered().compareTo(v1.getRegistered()));
                    votingResults.stream().findFirst().ifPresent(list::add);
                });
        return list.stream().collect(groupingBy(VotingResult::getRestaurant, counting()));
    }
}

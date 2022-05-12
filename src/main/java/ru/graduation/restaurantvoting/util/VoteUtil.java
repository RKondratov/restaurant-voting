package ru.graduation.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.graduation.restaurantvoting.model.Vote;
import ru.graduation.restaurantvoting.to.VoteTo;

@UtilityClass
public class VoteUtil {
    public static Vote createVoteFromTo(VoteTo voteTo) {
        return new Vote(voteTo.getRegistered(), voteTo.getRestaurantId(), voteTo.getUserId());
    }
}
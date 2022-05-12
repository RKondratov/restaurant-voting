package ru.graduation.restaurantvoting.to;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class VotingResultTo {
    @NotNull
    Integer restaurantId;

    @NotNull
    Long votes;
}
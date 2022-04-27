package ru.graduation.restaurantvoting.to;

import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.graduation.restaurantvoting.model.Restaurant;

import javax.validation.constraints.NotNull;

@Value
@EqualsAndHashCode(callSuper = true)
public class VotingResultTo extends BaseTo {
    @NotNull
    Restaurant restaurant;

    @NotNull
    Long votes;
}
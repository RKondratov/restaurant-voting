package ru.graduation.restaurantvoting.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@EqualsAndHashCode(callSuper = true)
public class VoteTo extends BaseTo{
    @NotNull
    LocalDateTime registered;

    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @NotNull
    Integer restaurantId;

    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @NotNull
    Integer userId;
}
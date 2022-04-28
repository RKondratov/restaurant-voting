package ru.graduation.restaurantvoting.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import ru.graduation.restaurantvoting.model.Restaurant;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@Data
@AllArgsConstructor
public class VoteTo {
    @NotNull
    LocalDateTime registered;

    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @NotNull
    Restaurant restaurant;

    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @NotNull
    Integer userId;
}
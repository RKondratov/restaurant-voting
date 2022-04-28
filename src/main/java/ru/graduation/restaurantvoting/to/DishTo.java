package ru.graduation.restaurantvoting.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.graduation.restaurantvoting.model.Restaurant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Value
@EqualsAndHashCode(callSuper = true)
public class DishTo extends BaseTo {
    @NotBlank
    @Size(min = 1, max = 256)
    String dishName;

    @NotNull
    Integer price;

    @NotNull
    Date registered;

    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @NotNull
    Restaurant restaurant;

    public DishTo(Integer id, String dishName, Integer price, Date registered, Restaurant restaurant) {
        super(id);
        this.dishName = dishName;
        this.price = price;
        this.registered = registered;
        this.restaurant = restaurant;
    }
}
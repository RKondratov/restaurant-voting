package ru.graduation.restaurantvoting.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends BaseTo {
    @NotBlank
    @Size(min = 1, max = 256)
    String name;

    public RestaurantTo(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
package ru.graduation.restaurantvoting.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Value
@EqualsAndHashCode(callSuper = true)
public class DishTo extends BaseTo {
    @NotBlank
    @Size(min = 1, max = 256)
    String name;

    @NotNull
    Integer price;

    @NotNull
    Date creationDate;

    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @NotNull
    Integer restaurantId;

    public DishTo(Integer id, String name, Integer price, Date creationDate, Integer restaurantId) {
        super(id);
        this.name = name;
        this.price = price;
        this.creationDate = creationDate;
        this.restaurantId = restaurantId;
    }
}
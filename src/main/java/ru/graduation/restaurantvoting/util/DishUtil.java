package ru.graduation.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.graduation.restaurantvoting.model.Dish;
import ru.graduation.restaurantvoting.to.DishTo;

@UtilityClass
public class DishUtil {

    public static Dish createDishFromTo(DishTo dishTo) {
        return new Dish(dishTo.getName(), dishTo.getPrice(), dishTo.getCreationDate(), dishTo.getRestaurantId());
    }

    public static Dish updateDishFromTo(Dish dish, DishTo dishTo) {
        dish.setName(dishTo.getName());
        dish.setPrice(dishTo.getPrice());
        dish.setRestaurantId(dishTo.getRestaurantId());
        return dish;
    }
}
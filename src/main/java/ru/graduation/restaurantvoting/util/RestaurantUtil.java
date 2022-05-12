package ru.graduation.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.graduation.restaurantvoting.model.Restaurant;
import ru.graduation.restaurantvoting.to.RestaurantTo;

@UtilityClass
public class RestaurantUtil {
    public static Restaurant createRestaurantFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(restaurantTo.getName());
    }

    public static Restaurant updateRestaurantFromTo(Restaurant restaurant, RestaurantTo restaurantTo) {
        restaurant.setName(restaurantTo.getName());
        return restaurant;
    }
}
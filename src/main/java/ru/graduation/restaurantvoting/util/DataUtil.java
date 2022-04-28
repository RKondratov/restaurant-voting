package ru.graduation.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.graduation.restaurantvoting.model.*;
import ru.graduation.restaurantvoting.to.DishTo;
import ru.graduation.restaurantvoting.to.RestaurantTo;
import ru.graduation.restaurantvoting.to.UserTo;
import ru.graduation.restaurantvoting.to.VoteTo;

import java.util.Date;

@UtilityClass
public class DataUtil {

    public static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static User createUserFromTo(UserTo userTo) {
        return new User(null, userTo.getFirstName(), userTo.getLastName(),
                userTo.getEmail().toLowerCase(), userTo.getPassword(), Role.USER);
    }

    public static User updateUserFromTo(User user, UserTo userTo) {
        user.setFirstName(userTo.getFirstName());
        user.setLastName(userTo.getLastName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static User prepareUserToSave(User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }

    public static Dish createDishFromTo(DishTo dishTo) {
        return new Dish(dishTo.getDishName(), dishTo.getPrice(), new Date(), dishTo.getRestaurant());
    }

    public static Dish updateDishFromTo(Dish dish, DishTo dishTo) {
        dish.setDishName(dishTo.getDishName());
        dish.setPrice(dishTo.getPrice());
        dish.setRestaurant(dishTo.getRestaurant());
        return dish;
    }

    public static Restaurant createRestaurantFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(restaurantTo.getName());
    }

    public static Restaurant updateRestaurantFromTo(Restaurant restaurant, RestaurantTo restaurantTo) {
        restaurant.setName(restaurantTo.getName());
        return restaurant;
    }

    public static VotingResult createVoteFromTo(VoteTo voteTo, User user) {
        return new VotingResult(voteTo.getRegistered(), voteTo.getRestaurant(), user);
    }
}
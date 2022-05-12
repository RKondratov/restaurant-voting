package ru.graduation.restaurantvoting.web;


import ru.graduation.restaurantvoting.model.Dish;
import ru.graduation.restaurantvoting.model.Restaurant;
import ru.graduation.restaurantvoting.model.Role;
import ru.graduation.restaurantvoting.model.User;
import ru.graduation.restaurantvoting.to.UserTo;
import ru.graduation.restaurantvoting.to.VoteTo;
import ru.graduation.restaurantvoting.to.VotingResultTo;
import ru.graduation.restaurantvoting.util.JsonUtil;

import java.time.LocalDateTime;

public class TestData {
    public static final int ADMIN_USER_ID = 1;
    public static final int USER_ID = 2;
    public static final int ADMIN_ID = 3;
    public static final int NOT_FOUND_ID = 22;
    public static final int ONE = 1;
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    public static final String ADMIN_USER_MAIL = "useradmin@mail.ru";
    public static final String USER_MAIL = "user@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static final MatcherFactory.Matcher<User> USER_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "password");
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "id");
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "id", "creationDate", "restaurant");
    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class, "id", "registered");
    public static final MatcherFactory.Matcher<VotingResultTo> VOTE_RESULT_TO_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(VotingResultTo.class, "id");

    public static final User USER_ADMIN_AND_USER =
            new User(ADMIN_USER_ID, "Rustam", "Kondratov", ADMIN_USER_MAIL, "pass", Role.ADMIN, Role.USER);
    public static final User USER_ADMIN =
            new User(ADMIN_ID, "Admin", "Adminov", ADMIN_MAIL, "pass", Role.ADMIN);
    public static final User USER_USER =
            new User(USER_ID, "User", "Userov", USER_MAIL, "pass", Role.USER);

    public static final Restaurant RESTAURANT_FIRST = new Restaurant(1, "restaurant1");
    public static final Restaurant RESTAURANT_SECOND = new Restaurant(2, "restaurant2");
    public static final Restaurant RESTAURANT_THIRD = new Restaurant(3, "restaurant3");

    public static final Dish DISH_FIRST = new Dish("soup", 500, null, 1);
    public static final Dish DISH_SECOND = new Dish("pancakes", 1000, null, 1);
    public static final Dish DISH_THIRD = new Dish("fish", 500, null, 3);

    public static final VotingResultTo VOTE_FIRST = new VotingResultTo(2, 1L);
    public static final VotingResultTo VOTE_SECOND = new VotingResultTo(3, 1L);

    public static final VoteTo VOTE_TO_FIRST = new VoteTo(LocalDateTime.of(2022, 01, 01, 10, 00, 00), 1, 2);

    public static UserTo getForUpdate() {
        return new UserTo(USER_ID, "UpdatedName", "UpdatedLastName", USER_MAIL, "newPass");
    }

    public static UserTo getNew() {
        return new UserTo(null, "New", "NewLastName", "new@gmail.com", "newPass");
    }

    public static User getUpdated(UserTo userTo) {
        return new User(USER_ID, userTo.getFirstName(), userTo.getLastName(), userTo.getEmail(), userTo.getPassword(), Role.USER);
    }

    public static String jsonWithPassword(UserTo user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
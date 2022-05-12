package ru.graduation.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.graduation.restaurantvoting.model.Role;
import ru.graduation.restaurantvoting.model.User;
import ru.graduation.restaurantvoting.to.UserTo;

@UtilityClass
public class UserUtil {
    private static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

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
}
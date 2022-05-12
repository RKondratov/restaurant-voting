package ru.graduation.restaurantvoting.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.graduation.restaurantvoting.model.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {
    @Transactional(readOnly = true)
    Optional<User> getByEmail(String email);
}
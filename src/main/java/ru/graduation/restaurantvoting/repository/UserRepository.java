package ru.graduation.restaurantvoting.repository;

import ru.graduation.restaurantvoting.model.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);
}

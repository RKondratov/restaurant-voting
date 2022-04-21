package ru.graduation.restaurantvoting.web.admin;

import ru.graduation.restaurantvoting.repository.BaseRepository;

import javax.validation.constraints.NotNull;

public abstract class AbstractAdminController {
    static final String BASE_ADMIN_URL = "/api/admin";

    public void delete(@NotNull BaseRepository<?> repository, int id) {
        repository.deleteExisted(id);
    }
}

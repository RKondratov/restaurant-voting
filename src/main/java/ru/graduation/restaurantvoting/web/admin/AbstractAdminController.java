package ru.graduation.restaurantvoting.web.admin;

import lombok.extern.slf4j.Slf4j;
import ru.graduation.restaurantvoting.repository.BaseRepository;

import javax.validation.constraints.NotNull;

@Slf4j
public abstract class AbstractAdminController {
    static final String BASE_ADMIN_URL = "/api/admin";

    public void delete(@NotNull BaseRepository<?> repository, int id) {
        log.info("delete {} with id = {}", repository.getClass().getSimpleName(), id);
        repository.deleteExisted(id);
    }
}
package ru.graduation.restaurantvoting.web.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.restaurantvoting.model.Meal;
import ru.graduation.restaurantvoting.repository.MealRepository;

import javax.validation.Valid;
import java.net.URI;

import static ru.graduation.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static ru.graduation.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminMealController.REST_URL)
@Slf4j
public class AdminMealController extends AbstractAdminController {
    @Autowired
    protected MealRepository mealRepository;

    static final String REST_URL = BASE_ADMIN_URL + "/meals";

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> create(@Valid @RequestBody Meal meal) {
        checkNew(meal);
        log.info("create {}", meal);
        final Meal created = mealRepository.save(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{mealId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void update(@Valid @RequestBody Meal meal, @PathVariable int mealId) {
        log.info("update meal with id = {}", mealId);
        assureIdConsistent(meal, mealId);
        mealRepository.save(meal);
    }

    @DeleteMapping("/{mealId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int mealId) {
        super.delete(mealRepository, mealId);
    }
}

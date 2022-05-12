package ru.graduation.restaurantvoting.web.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.restaurantvoting.model.Dish;
import ru.graduation.restaurantvoting.repository.DishRepository;
import ru.graduation.restaurantvoting.to.DishTo;

import javax.validation.Valid;
import java.net.URI;

import static ru.graduation.restaurantvoting.util.DishUtil.createDishFromTo;
import static ru.graduation.restaurantvoting.util.DishUtil.updateDishFromTo;
import static ru.graduation.restaurantvoting.util.validation.ValidationUtil.*;

@RestController
@RequestMapping(value = AdminDishController.REST_URL)
@Slf4j
public class AdminDishController extends AbstractAdminController {
    @Autowired
    protected DishRepository dishRepository;
    static final String REST_URL = BASE_ADMIN_URL + "/dishes";

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Dish> create(@Valid @RequestBody DishTo dishTo) {
        checkNew(dishTo);
        checkDish(dishTo);
        log.info("create {}", dishTo);
        final Dish created = dishRepository.save(createDishFromTo(dishTo));
        final URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody DishTo dishTo, @PathVariable int id) {
        log.info("update dish with id = {}", id);
        assureIdConsistent(dishTo, id);
        dishRepository.findById(id).ifPresent(dish -> dishRepository.save(updateDishFromTo(dish, dishTo)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> get(@PathVariable int id) {
        log.info("get dish by id = {}", id);
        return ResponseEntity.of(dishRepository.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(dishRepository, id);
    }
}
package ru.graduation.restaurantvoting.web.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.restaurantvoting.model.Restaurant;
import ru.graduation.restaurantvoting.repository.RestaurantRepository;
import ru.graduation.restaurantvoting.to.RestaurantTo;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.graduation.restaurantvoting.util.RestaurantUtil.createRestaurantFromTo;
import static ru.graduation.restaurantvoting.util.RestaurantUtil.updateRestaurantFromTo;
import static ru.graduation.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static ru.graduation.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL)
@Slf4j
public class AdminRestaurantController extends AbstractAdminController {
    @Autowired
    protected RestaurantRepository restaurantRepository;
    static final String REST_URL = BASE_ADMIN_URL + "/restaurants";

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("getRestaurants");
        return restaurantRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get restaurant with id = {}", id);
        return ResponseEntity.of(restaurantRepository.findById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody RestaurantTo restaurantTo) {
        checkNew(restaurantTo);
        log.info("create {}", restaurantTo);
        final Restaurant created = restaurantRepository.save(createRestaurantFromTo(restaurantTo));
        final URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody RestaurantTo restaurantTo, @PathVariable int id) {
        assureIdConsistent(restaurantTo, id);
        log.info("update restaurant with id = {}", id);
        restaurantRepository.findById(id)
                .ifPresent(restaurant -> restaurantRepository.save(updateRestaurantFromTo(restaurant, restaurantTo)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(restaurantRepository, id);
    }
}
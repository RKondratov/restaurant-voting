package ru.graduation.restaurantvoting.web.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.restaurantvoting.model.Restaurant;
import ru.graduation.restaurantvoting.repository.RestaurantRepository;
import ru.graduation.restaurantvoting.to.RestaurantTo;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.graduation.restaurantvoting.util.DataUtil.createRestaurantFromTo;
import static ru.graduation.restaurantvoting.util.DataUtil.updateRestaurantFromTo;
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
    public List<Restaurant> getRestaurants() {
        log.info("getRestaurants");
        return restaurantRepository.findAll();
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable int restaurantId) {
        log.info("get restaurant with id = {}", restaurantId);
        return ResponseEntity.of(restaurantRepository.findById(restaurantId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody RestaurantTo restaurantTo) {
        checkNew(restaurantTo);
        log.info("create {}", restaurantTo);
        final Restaurant created = restaurantRepository.save(createRestaurantFromTo(restaurantTo));
        final URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody RestaurantTo restaurantTo, @PathVariable int restaurantId) {
        assureIdConsistent(restaurantTo, restaurantId);
        log.info("update restaurant with id = {}", restaurantId);
        restaurantRepository.findById(restaurantId)
                .ifPresent(restaurant -> restaurantRepository.save(updateRestaurantFromTo(restaurant, restaurantTo)));
    }

    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId) {
        super.delete(restaurantRepository, restaurantId);
    }
}

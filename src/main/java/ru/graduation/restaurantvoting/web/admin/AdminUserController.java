package ru.graduation.restaurantvoting.web.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.restaurantvoting.model.User;
import ru.graduation.restaurantvoting.repository.UserRepository;
import ru.graduation.restaurantvoting.to.UserTo;
import ru.graduation.restaurantvoting.util.UserUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.graduation.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static ru.graduation.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminUserController.REST_URL)
@Slf4j
public class AdminUserController extends AbstractAdminController {
    @Autowired
    protected UserRepository userRepository;

    static final String REST_URL = BASE_ADMIN_URL + "/users";

    @GetMapping
    public List<User> getUsers() {
        log.info("getUsers");
        return userRepository.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable int userId) {
        log.info("get user with id = {}", userId);
        return ResponseEntity.of(userRepository.findById(userId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@Valid @RequestBody UserTo userTo) {
        checkNew(userTo);
        log.info("create {}", userTo);
        final User created = userRepository.save(UserUtil.createNewFromTo(userTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void update(@Valid @RequestBody UserTo userTo, @PathVariable int userId) {
        assureIdConsistent(userTo, userId);
        log.info("update user with id = {}", userId);
        userRepository.findById(userId).ifPresent(user -> userRepository.save(UserUtil.updateFromTo(user, userTo)));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int userId) {
        super.delete(userRepository, userId);
    }
}

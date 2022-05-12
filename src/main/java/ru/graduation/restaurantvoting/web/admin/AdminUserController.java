package ru.graduation.restaurantvoting.web.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.restaurantvoting.model.User;
import ru.graduation.restaurantvoting.repository.UserRepository;
import ru.graduation.restaurantvoting.to.UserTo;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.graduation.restaurantvoting.util.UserUtil.*;
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
    public List<User> getAll() {
        log.info("getUsers");
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable int id) {
        log.info("get user with id = {}", id);
        return ResponseEntity.of(userRepository.findById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> create(@Valid @RequestBody UserTo userTo) {
        checkNew(userTo);
        log.info("create {}", userTo);
        final User created = userRepository.save(prepareUserToSave(createUserFromTo(userTo)));
        final URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody UserTo userTo, @PathVariable int id) {
        assureIdConsistent(userTo, id);
        log.info("update user with id = {}", id);
        userRepository.findById(id)
                .ifPresent(user -> userRepository.save(prepareUserToSave(updateUserFromTo(user, userTo))));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(userRepository, id);
    }
}
package ru.yandex.practicum.filmrate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmrate.model.User;
import ru.yandex.practicum.filmrate.service.UserService;
import ru.yandex.practicum.filmrate.service.ValidationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ValidationService validationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAllUsers() {
        log.info("Список пользователей получен.");
        return userService.findAllUser();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User findUser(@PathVariable int id) {
        User foundUser = userService.findUserById(id);
        log.info("Пользователь с id={} получен.", foundUser.getId());
        return foundUser;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@Valid @RequestBody User user) {
        validationService.validateUser(user);
        User addedUser = userService.addUser(user);
        log.info("Пользователь с id={} был добавлен.", addedUser.getId());
        return addedUser;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User updateOrAddUser(@Valid @RequestBody User user) {
        validationService.validateUser(user);
        User updatedUser = userService.updateUser(user);
        log.info("Пользователь с id={} обновлён.", updatedUser.getId());
        return updatedUser;
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllUsers() {
        userService.deleteAllUsers();
        log.info("Все пользователи были удалены.");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
        log.info("Пользователь c id={} был удалён.", id);
    }
}
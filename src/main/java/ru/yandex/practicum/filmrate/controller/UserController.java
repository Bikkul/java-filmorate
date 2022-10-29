package ru.yandex.practicum.filmrate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmrate.exception.ControllerValidationException;
import ru.yandex.practicum.filmrate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    private int id = 0;
    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping("/users")
    public Collection<User> findAllUsers() {
        log.info("Список пользователей получен.");
        return users.values();
    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            validateUser(user);
            user.setId(generatedId());
            users.put(user.getId(), user);
            log.debug("Пользователь создан.");
        }
        return user;
    }

    @PutMapping("/users")
    public User updateOrAddUser(@Valid @RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            log.warn("Указан несуществующий ID={}", user.getId());
            throw new ControllerValidationException("Невозможно обновить user");
        }
        validateUser(user);
        users.put(user.getId(), user);
        log.debug("Пользователь с Id={} обновлён.", user.getId());
        return user;
    }

    private int generatedId() {
        return ++id;
    }

    private void validateUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.warn("Указан неверный формат email, указан email={}.", user.getEmail());
            throw new ControllerValidationException("Указан неверный email.");
        }

        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Дата рождения не может быть в будущем. Сейчас {}, указанная дата {}", LocalDate.now(),
                    user.getBirthday());
            throw new ControllerValidationException("Указана неверная дата рождения");
        }

        if (user.getLogin() == null || user.getLogin().contains(" ") || user.getLogin().isBlank()) {
            log.warn("Логин не может содержать пробелы или быть пустым, указанный логин={}.", user.getLogin());
            throw new ControllerValidationException("Указан неверный логин.");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            log.debug("Имя не установлено, имя взято из логина. Установлено имя={} ", user.getLogin());
            user.setName(user.getLogin());
        }
    }
}
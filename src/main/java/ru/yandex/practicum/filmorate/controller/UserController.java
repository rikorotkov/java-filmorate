package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Collection<User> getUsers() {
        return userService.findAllUsers();
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        if (user.getId() != null) {
            throw new ValidationException("ID должен быть пустым для нового пользователя");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        if (user.getId() == null || !userService.existsById(user.getId())) {
            throw new NotFoundException("Пользователь не найден");
        }
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable Long id) {
        return userService.findUserById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @GetMapping("/{userId}/friends")
    public List<User> findFriends(@PathVariable Long userId) {
        return userService.findAllFriends(userId);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        userService.addFriend(userId, friendId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Map<String, String>> removeFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        Map<String, String> response = new HashMap<>();

        //not friend remove - 200, 204
        // дружеская взаимность
        if (userService.isFriend(userId, friendId) || userService.isFriend(friendId, userId)) {
            userService.removeFriend(userId, friendId);
            userService.removeFriend(friendId, userId);
            return ResponseEntity.noContent().build();
        }

        if (userService.isFriend(friendId, userId)) {
            response.put("error", "Дружба не существует");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        if (!userService.existsById(userId) || !userService.existsById(friendId)) {
            response.put("error", "Пользователь не найден");
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }

        if (!userService.isFriend(userId, friendId)) {
            response.put("error", "Дружба не существует");
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }

        if (!userService.isFriend(friendId, userId)) {
            response.put("error", "Дружба не существует");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        userService.removeFriend(userId, friendId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/friends/common/{friendId}")
    public ResponseEntity<List<User>> findCommonFriends(@PathVariable Long id, @PathVariable Long friendId) {
        List<User> commonFriends = userService.findCommonFriends(id, friendId);
        return ResponseEntity.ok().body(commonFriends);
    }

}

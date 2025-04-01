package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserServiceImpl(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public Collection<User> findAllUsers() {
        return userStorage.findAllUsers();
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return Optional.ofNullable(userStorage.findUserById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден")));
    }

    @Override
    public User createUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getFriends() == null) {
            user.setFriends(new HashSet<>());
        }
        return userStorage.createUser(user);
    }


    @Override
    public User updateUser(User user) {
        if (!userStorage.existsById(user.getId())) {
            throw new NotFoundException("Пользователь с id " + user.getId() + " не найден");
        }
        return userStorage.updateUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userStorage.existsById(id)) {
            throw new NotFoundException("Пользователь с id " + id + " не найден");
        }
        userStorage.deleteUser(id);
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        if (userId.equals(friendId)) {
            throw new ValidationException("Нельзя добавить себя в друзья");
        }
        if (!existsById(userId) || !existsById(friendId)) {
            throw new NotFoundException("Пользователь не найден");
        }
        if (isFriend(userId, friendId)) {
            throw new ValidationException("Уже в друзьях");
        }
        userStorage.addToFriends(userId, friendId);
    }

    @Override
    public void removeFriend(Long userId, Long friendId) {
        userStorage.removeFriend(userId, friendId);
    }


    @Override
    public List<User> findAllFriends(Long id) {
        if (!userStorage.existsById(id)) {
            throw new NotFoundException("Пользователь не найден");
        }
        return userStorage.findFriends(id);
    }

    @Override
    public List<User> findCommonFriends(Long id, Long friendId) {
        if (!userStorage.existsById(id)) {
            throw new NotFoundException("Пользователь с id " + id + " не найден");
        }
        if (!userStorage.existsById(friendId)) {
            throw new NotFoundException("Пользователь с id " + friendId + " не найден");
        }

        return new ArrayList<>(userStorage.findCommonFriends(id, friendId));
    }

    @Override
    public boolean existsById(long id) {
        return userStorage.existsById(id);
    }

    @Override
    public boolean isFriend(Long userId, Long friendId) {
        return userStorage.isFriend(userId, friendId);
    }

    private void validateUsers(Long userId, Long friendId) {
        if (userId.equals(friendId)) {
            throw new ValidationException("Нельзя добавить/удалить самого себя");
        }
        if (!userStorage.existsById(userId)) {
            throw new NotFoundException("Пользователь не найден");
        }
        if (!userStorage.existsById(friendId)) {
            throw new NotFoundException("Друг не найден");
        }
    }
}
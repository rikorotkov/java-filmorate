package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;

    @Override
    public Collection<User> findAllUsres() {
        return userStorage.findAllUsers();
    }

    @Override
    public Optional<User> findUserById(long id) {
        return Optional.empty();
    }

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public void addFried(Long id, Long friendId) {

    }

    @Override
    public void removeFried(Long id, Long friendId) {

    }

    @Override
    public Collection<User> findAllFrieds(Long id) {
        return List.of();
    }

    @Override
    public Collection<User> findCommonFrieds(Long id, Long friendId) {
        return List.of();
    }

}

package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    Collection<User> findAllUsres();

    Optional<User> findUserById(long id);

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Long id);

    void addFried(Long id, Long friendId);

    void removeFried(Long id, Long friendId);

    Collection<User> findAllFrieds(Long id);

    Collection<User> findCommonFrieds(Long id, Long friendId);

}

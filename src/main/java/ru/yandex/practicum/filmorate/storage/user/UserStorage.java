package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface UserStorage {

    User createUser(User user);

    User updateUser(User user);

    Collection<User> findAllUsers();

    Optional<User> findUserById(Long id);

    void deleteUser(Long id);

    void addToFriends(Long userId, Long friendId);

    void removeFriend(Long id, Long friendId);

    Set<User> findCommonFriends(Long id, Long friendId);

    Set<User> findFriends(Long id);

}

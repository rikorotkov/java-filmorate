package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Collection<User> findAllUsers();

    Optional<User> findUserById(Long id);

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Long id);

    void addFriend(Long id, Long friendId);

    void removeFriend(Long id, Long friendId);

    List<User> findAllFriends(Long id);

    List<User> findCommonFriends(Long id, Long friendId);

    boolean existsById(long id);

    boolean isFriend(Long userId, Long friendId);

}

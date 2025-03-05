package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Set;

public interface UserService {

    Collection<User> findAllUsers();

    User findUserById(Long id);

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Long id);

    void addFriend(Long id, Long friendId);

    void removeFriend(Long id, Long friendId);

    Set<Long> findAllFriends(Long id);

    Set<Long> findCommonFriends(Long id, Long friendId);

}

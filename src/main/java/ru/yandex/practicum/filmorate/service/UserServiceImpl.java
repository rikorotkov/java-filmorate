package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;

    @Override
    public Collection<User> findAllUsers() {
        return userStorage.findAllUsers();
    }

    @Override
    public User findUserById(Long id) {
        return userStorage.findUserById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    @Override
    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        userStorage.deleteUser(id);
    }

    @Override
    public void addFriend(Long id, Long friendId) {
        userStorage.addToFriends(id, friendId);
    }

    @Override
    public void removeFriend(Long id, Long friendId) {
        userStorage.removeFriend(id, friendId);
    }

    @Override
    public Set<Long> findAllFriends(Long id) {
        return userStorage.findFriends(id);
    }

    @Override
    public Set<Long> findCommonFriends(Long id, Long friendId) {
        return userStorage.findCommonFriends(id, friendId);
    }

}

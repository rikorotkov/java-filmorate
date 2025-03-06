package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private long idCounter = 1;

    @Override
    public User createUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(idCounter++);
        users.put(user.getId(), user);
        return user;
    }

    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("Пользователь с id " + user.getId() + " не найден");
        }

        User existingUser = users.get(user.getId());
        existingUser.setEmail(user.getEmail());
        existingUser.setLogin(user.getLogin());
        existingUser.setName(user.getName() != null && !user.getName().isBlank() ? user.getName() : existingUser.getLogin());
        existingUser.setBirthday(user.getBirthday());

        return existingUser;
    }

    @Override
    public Collection<User> findAllUsers() {
        return users.values();
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return users.containsKey(id) ? Optional.of(users.get(id)) : Optional.empty();
    }

    @Override
    public void deleteUser(Long id) {
        users.remove(id);
    }

    @Override
    public void addToFriends(Long userId, Long friendId) {
        User user = users.get(userId);
        User friend = users.get(friendId);

        if (user == null || friend == null) {
            throw new NotFoundException("Один из пользователей не найден");
        }

        if (user.getFriends().contains(friendId)) {
            throw new IllegalArgumentException("Пользователь уже в друзьях");
        }

        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    @Override
    public void removeFriend(Long id, Long friendId) {
        User user = users.get(id);
        User friend = users.get(friendId);

        if (user == null || friend == null) {
            throw new NotFoundException("Один из пользователей не найден");
        }

        if (user.getFriends().contains(friendId)) {
            user.getFriends().remove(friendId);
            friend.getFriends().remove(id);
        }
    }

    @Override
    public Set<Long> findCommonFriends(Long id, Long friendId) {
        User user = users.get(id);
        User friend = users.get(friendId);

        if (user == null || friend == null) {
            throw new NotFoundException("Один из пользователей не найден");
        }

        return user.getFriends().stream()
                .filter(friend.getFriends()::contains)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Long> findFriends(Long id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("Пользователь не найден");
        }
        return users.get(id).getFriends();
    }

}

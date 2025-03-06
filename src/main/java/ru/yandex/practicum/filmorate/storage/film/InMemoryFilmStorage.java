package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.WrongReleaseDateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private final UserStorage userStorage;
    private long idCounter = 1;

    public InMemoryFilmStorage(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public Film createFilm(Film film) {
        film.setId(idCounter++);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (film.getId() == null || !films.containsKey(film.getId())) {
            throw new NotFoundException("Не найден фильм с id - " + film.getId());
        }
        validateFilmRelease(film);

        Film updateFilm = films.get(film.getId());
        updateFilm.setName(film.getName());
        updateFilm.setReleaseDate(film.getReleaseDate());
        updateFilm.setDescription(film.getDescription());
        updateFilm.setDuration(film.getDuration());

        films.put(film.getId(), updateFilm);

        return updateFilm;
    }

    @Override
    public Collection<Film> findAllFilms() {
        return films.values();
    }

    @Override
    public Optional<Film> findFilmById(long id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public void deleteFilm(Long id) {
        films.remove(id);
    }

    @Override
    public void likeFilm(Long filmId, Long userId) {
        Film film = films.get(filmId);
        Optional<User> user = userStorage.findUserById(userId);

        if (user.isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }
        if (film == null) {
            throw new NotFoundException("Фильм не найден");
        }

        film.getUsersLike().add(userId);

    }

    @Override
    public void dislikeFilm(Long userId, Long filmId) {
        Film film = films.get(filmId);
        Optional<User> user = userStorage.findUserById(userId);

        if (user.isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }
        if (film == null) {
            throw new NotFoundException("Фильм не найден");
        }
        if (!film.getUsersLike().contains(userId)) {
            throw new NotFoundException("Не найден лайк от пользователя");
        }

        film.getUsersLike().remove(userId);
    }

    @Override
    public Collection<Film> findFilmsByTopLikes(int count) {
        return films.values().stream()
                .sorted(Comparator.comparingInt((Film f) -> f.getUsersLike().size()).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    private void validateFilmRelease(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new WrongReleaseDateException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }
    }

}

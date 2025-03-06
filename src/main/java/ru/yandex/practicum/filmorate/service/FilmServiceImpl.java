package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmStorage filmStorage;

    @Override
    public Collection<Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    @Override
    public Optional<Film> findFilmById(Long id) {
        return filmStorage.findFilmById(id);
    }

    @Override
    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    @Override
    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    @Override
    public void deleteFilm(Long id) {
        filmStorage.deleteFilm(id);
    }

    @Override
    public void likeFilm(Long userId, Long filmId) {
        filmStorage.likeFilm(userId, filmId);
    }

    @Override
    public void dislikeFilm(Long userId, Long filmId) {
        filmStorage.dislikeFilm(userId, filmId);
    }

    @Override
    public Collection<Film> findFilmsByTopLikes(int count) {
        return filmStorage.findFilmsByTopLikes(count);
    }

}

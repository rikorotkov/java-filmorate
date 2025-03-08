package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmService {

    Collection<Film> findAllFilms();

    Optional<Film> findFilmById(Long id);

    Film createFilm(Film film);

    Film updateFilm(Film film);

    void deleteFilm(Long id);

    void likeFilm(Long userId, Long filmId);

    void dislikeFilm(Long userId, Long filmId);

    Collection<Film> findFilmsByTopLikes(int count);

}

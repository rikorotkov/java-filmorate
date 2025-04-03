package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    Film createFilm(Film film);

    Film updateFilm(Film film);

    Collection<Film> findAllFilms();

    Film findFilmById(long id);

    void deleteFilm(Long id);

    void likeFilm(Long userId, Long filmId);

    void dislikeFilm(Long userId, Long filmId);

    Collection<Film> findFilmsByTopLikes(int count);

    boolean existsById(long id);

}

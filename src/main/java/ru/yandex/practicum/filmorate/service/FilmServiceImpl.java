package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreDao;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDao;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
public class FilmServiceImpl implements FilmService {

    private final FilmStorage filmStorage;
    private final MpaDao mpaDao;
    private final GenreDao genreDao;

    @Autowired
    public FilmServiceImpl(@Qualifier("filmDbStorage") FilmStorage filmStorage, MpaDao mpaDao, GenreDao genreDao) {
        this.filmStorage = filmStorage;
        this.mpaDao = mpaDao;
        this.genreDao = genreDao;
    }

    @Override
    public Collection<Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    @Override
    public Optional<Film> findFilmById(Long id) {
        return Optional.ofNullable(filmStorage.findFilmById(id));
    }

    @Override
    public Film createFilm(Film film) {
        film.setUsersLike(new HashSet<>());
        if (film.getMpa() == null || !mpaDao.existsById(film.getMpa().getId())) {
            throw new NotFoundException("Мпа рейтинг с id " + film.getMpa().getId() + " не найден");
        }
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                if (!genreDao.existsById(genre.getId())) {
                    throw new NotFoundException("Жанр с id " + genre.getId() + " не найден");
                }
            }
        }
        return filmStorage.createFilm(film);
    }

    @Override
    public Film updateFilm(Film film) {
        if (film.getMpa() == null || !mpaDao.existsById(film.getMpa().getId())) {
            throw new NotFoundException("Мпа рейтинг не найден");
        }
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                if (!genreDao.existsById(genre.getId())) {
                    throw new NotFoundException("Жанр с id " + genre.getId() + " не найден");
                }
            }
        }
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
        if (count <= 0) {
            throw new ValidationException("Количество не может быть < 0");
        }
        return filmStorage.findFilmsByTopLikes(count);
    }

    @Override
    public boolean existsById(long id) {
        try {
            return filmStorage.existsById(id);
        } catch (Exception e) {
            throw new NotFoundException("Фильм не найден");
        }
    }

}

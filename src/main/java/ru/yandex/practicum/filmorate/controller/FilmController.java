package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.WrongReleaseDateException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Long, Film> films = new HashMap<>();
    private long idCounter = 1;

    @GetMapping
    public Collection<Film> getFilms() {
        return films.values();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) throws WrongReleaseDateException {
        if (film.getId() == null || film.getId() == 0) {
            film.setId(idCounter++);
        }
        validateFilmRelease(film);
        films.put(film.getId(), film);
        log.info("Добавлен фильм: {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws NotFoundException, WrongReleaseDateException {
        log.info("Обновление фильма: {}", film);

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

        log.info("Обновлен фильм: {}", updateFilm);
        return updateFilm;
    }


    private void validateFilmRelease(Film film) throws WrongReleaseDateException {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new WrongReleaseDateException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }
    }
}
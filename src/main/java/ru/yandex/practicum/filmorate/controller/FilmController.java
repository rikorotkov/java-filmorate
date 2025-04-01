package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.genre.GenreDao;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDao;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/films")
@AllArgsConstructor
public class FilmController {

    private final FilmService filmService;
    private final UserService userService;
    private final GenreDao genreService;
    private final MpaDao mpaService;

    @GetMapping
    public Collection<Film> getFilms() {
        return filmService.findAllFilms();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        if (film.getId() != null) {
            throw new ValidationException("ID должен быть пустым для нового фильма");
        }
        if (film.getMpa() == null) {
            throw new ValidationException("MPA не может быть пустым ");
        }
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (film.getId() == null || !filmService.existsById(film.getId())) {
            throw new NotFoundException("Фильм не найден");
        }
        return filmService.updateFilm(film);
    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable Long id) {
        filmService.deleteFilm(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void likeFilm(@PathVariable long id, @PathVariable long userId) {
        filmService.likeFilm(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void dislikeFilm(@PathVariable Long id, @PathVariable Long userId) {
        if (!filmService.existsById(id)) {
            throw new NotFoundException("Фильм не найден");
        }
        if (!userService.existsById(userId)) {
            throw new NotFoundException("Пользователь не найден");
        }
        filmService.dislikeFilm(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getTopFilms(@RequestParam(defaultValue = "10") int count) {
        if (count <= 0) {
            throw new ValidationException("Параметр count должен быть положительным");
        }
        return filmService.findFilmsByTopLikes(count);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable long id) {
        return filmService.findFilmById(id)
                .orElseThrow(() -> new NotFoundException("Фильм с id " + id + " не найден"));
    }

}
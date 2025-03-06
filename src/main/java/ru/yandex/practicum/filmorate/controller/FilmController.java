package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/films")
@AllArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public Collection<Film> getFilms() {
        return filmService.findAllFilms();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@Valid @PathVariable Long id) {
        filmService.deleteFilm(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void likeFilm(@PathVariable Long id, @PathVariable Long userId) {
        filmService.likeFilm(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void dislikeFilm(@PathVariable Long id, @PathVariable Long userId) {
        filmService.dislikeFilm(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getTopFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.findFilmsByTopLikes(count);
    }

//    @PostMapping
//    public Film addFilm(@Valid @RequestBody Film film) {
//        if (film.getId() == null || film.getId() == 0) {
//            film.setId(idCounter++);
//        }
//        validateFilmRelease(film);
//        films.put(film.getId(), film);
//        log.info("Добавлен фильм: {}", film);
//        return film;
//    }
//
//    @PutMapping
//    public Film updateFilm(@Valid @RequestBody Film film) {
//        log.info("Обновление фильма: {}", film);
//
//        if (film.getId() == null || !films.containsKey(film.getId())) {
//            throw new NotFoundException("Не найден фильм с id - " + film.getId());
//        }
//        validateFilmRelease(film);
//
//        Film updateFilm = films.get(film.getId());
//        updateFilm.setName(film.getName());
//        updateFilm.setReleaseDate(film.getReleaseDate());
//        updateFilm.setDescription(film.getDescription());
//        updateFilm.setDuration(film.getDuration());
//
//        films.put(film.getId(), updateFilm);
//
//        log.info("Обновлен фильм: {}", updateFilm);
//        return updateFilm;
//    }
//
//
//    private void validateFilmRelease(Film film) {
//        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
//            throw new WrongReleaseDateException("Дата релиза не может быть раньше 28 декабря 1895 года");
//        }
//    }
}
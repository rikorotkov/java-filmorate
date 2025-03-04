package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.WrongReleaseDateException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private long idCounter = 1;

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

    private void validateFilmRelease(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new WrongReleaseDateException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }
    }

}

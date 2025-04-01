package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;
import java.util.Optional;

public interface MpaDao {

    List<MpaRating> findAll();

    Optional<MpaRating> findById(int id);

    boolean existsById(int id);

}

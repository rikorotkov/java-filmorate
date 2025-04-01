package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDao;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/mpa")
public class MpaController {

    private final MpaDao mpaService;

    @GetMapping
    public List<MpaRating> findAll() {
        return mpaService.findAll();
    }

    @GetMapping("/{id}")
    public MpaRating findById(@PathVariable int id) {
        return mpaService.findById(id)
                .orElseThrow(() -> new NotFoundException("Категория рэйтинга не найдена"));
    }
}


package ru.yandex.practicum.filmorate.exception;

public class WrongReleaseDateException extends RuntimeException {
    public WrongReleaseDateException(String msg) {
        super(msg);
    }
}

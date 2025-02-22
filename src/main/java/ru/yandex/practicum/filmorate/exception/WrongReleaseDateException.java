package ru.yandex.practicum.filmorate.exception;

public class WrongReleaseDateException extends Exception {
    public WrongReleaseDateException(String msg) {
        super(msg);
    }
}

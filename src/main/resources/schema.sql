-- Таблица рейтингов MPA
CREATE TABLE IF NOT EXISTS mpa_ratings
(
    mpa_id INTEGER PRIMARY KEY,
    name   VARCHAR(10) NOT NULL UNIQUE
);

-- Таблица жанров
CREATE TABLE IF NOT EXISTS genres
(
    genre_id INTEGER PRIMARY KEY,
    name     VARCHAR(50) NOT NULL UNIQUE
);

-- Таблица пользователей
CREATE TABLE IF NOT EXISTS users
(
    user_id  BIGINT PRIMARY KEY AUTO_INCREMENT,
    email    VARCHAR(255) NOT NULL UNIQUE,
    login    VARCHAR(255) NOT NULL UNIQUE,
    name     VARCHAR(255),
    birthday DATE         NOT NULL
);

-- Таблица фильмов
CREATE TABLE IF NOT EXISTS films
(
    film_id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(255) NOT NULL,
    description  VARCHAR(200),
    release_date DATE         NOT NULL,
    duration     INTEGER      NOT NULL,
    mpa_id       INTEGER,
    FOREIGN KEY (mpa_id) REFERENCES mpa_ratings (mpa_id)
);

-- Связь фильмов и жанров (многие ко многим)
CREATE TABLE IF NOT EXISTS film_genres
(
    film_id  BIGINT  NOT NULL,
    genre_id INTEGER NOT NULL,
    PRIMARY KEY (film_id, genre_id),
    FOREIGN KEY (film_id) REFERENCES films (film_id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres (genre_id) ON DELETE CASCADE
);

-- Таблица лайков фильмов
CREATE TABLE IF NOT EXISTS film_likes
(
    film_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (film_id, user_id),
    FOREIGN KEY (film_id) REFERENCES films (film_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);

-- Таблица дружбы между пользователями
CREATE TABLE IF NOT EXISTS friendships
(
    user_id    BIGINT NOT NULL,
    friend_id  BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    FOREIGN KEY (friend_id) REFERENCES users (user_id) ON DELETE CASCADE
);
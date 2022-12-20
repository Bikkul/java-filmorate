DROP TABLE IF EXISTS USERS, FILMS, USERS_FRIENDS, FILMS_GENRES, GENRES, MPA_RATINGS, LIKES CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    user_id   INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email     VARCHAR(50) NOT NULL,
    login     VARCHAR(20) NOT NULL,
    user_name VARCHAR(20) NOT NULL,
    birthday  DATE
);

CREATE TABLE IF NOT EXISTS users_friends
(
    user_id   INT REFERENCES users (user_id) ON DELETE CASCADE,
    friend_id INT REFERENCES users (user_id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, friend_id)
);

CREATE TABLE IF NOT EXISTS mpa_ratings
(
    mpa_id   INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    mpa_name VARCHAR(8) NOT NULL
);

CREATE TABLE IF NOT EXISTS films
(
    film_id     INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name        VARCHAR(72)  NOT NULL,
    description VARCHAR(255) NOT NULL,
    releaseDate DATE,
    duration    INT,
    rate        INT,
    mpa_id      INT          REFERENCES mpa_ratings (Mpa_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS genres
(
    genre_id   INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    genre_name VARCHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS films_genres
(
    film_id  INT REFERENCES films (film_id) ON DELETE CASCADE,
    genre_id INT REFERENCES genres (genre_id) ON DELETE CASCADE,
    PRIMARY KEY (film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS likes
(
    film_id INT REFERENCES films (film_id) ON DELETE CASCADE,
    user_id INT REFERENCES users (user_id) ON DELETE CASCADE,
    PRIMARY KEY (film_id, user_id)
);

CREATE TABLE IF NOT EXISTS reviews
(
    review_id  INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    content    VARCHAR(255) NOT NULL,
    isPositive BOOLEAN,
    film_id    INT REFERENCES films (film_id) ON DELETE CASCADE,
    user_id    INT REFERENCES users (user_id) ON DELETE CASCADE,
    useful     INT
);

CREATE TABLE IF NOT EXISTS directors
(
    director_id   INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    director_name VARCHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS directors_film
(
    film_id     INT REFERENCES films (film_id) ON DELETE CASCADE,
    director_id INT REFERENCES directors (director_id) ON DELETE CASCADE,
    PRIMARY KEY (film_id, director_id)
);

CREATE TABLE IF NOT EXISTS event_types
(
    event_type_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name          VARCHAR(16) NOT NULL
);

CREATE TABLE IF NOT EXISTS operations
(
    operation_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name         VARCHAR(16) NOT NULL
);

CREATE TABLE IF NOT EXISTS feed
(
    event_id      INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    time_feed     TIMESTAMP,
    event_type_id INT REFERENCES event_types (event_type_id) ON DELETE SET NULL,
    operation_id  INT REFERENCES operations (operation_id) ON DELETE SET NULL,
    user_id       INT REFERENCES users (user_id) ON DELETE CASCADE,
    entity_id     INT
);
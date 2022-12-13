MERGE INTO mpa_ratings (mpa_id, name)
    VALUES (1, 'G'),
           (2, 'PG'),
           (3, 'PG-13'),
           (4, 'R'),
           (5, 'NC-17');

MERGE INTO genres (genre_id, name)
    VALUES (1, 'Боевик'),
           (2, 'Драма'),
           (3, 'Комедия'),
           (4, 'Фэнтези'),
           (5, 'Приключение'),
           (6, 'Вестерн'),
           (7, 'Документальный'),
           (8, 'Мелодрама'),
           (9, 'фантастика');

MERGE INTO FRIENDSHIPS_STATUS (STATUS_ID, STATUS)
    VALUES (1, 'Дружба не подтверждена'),
           (2, 'Дружба подтверждена');

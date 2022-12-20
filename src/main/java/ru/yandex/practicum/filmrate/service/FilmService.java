package ru.yandex.practicum.filmrate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmrate.dao.*;
import ru.yandex.practicum.filmrate.exception.NotFoundException;
import ru.yandex.practicum.filmrate.model.Film;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
	private final FilmStorage filmStorage;
	private final UserStorage userStorage;
	private final LikeStorage likeStorage;
	private final FilmGenreStorage filmGenreStorage;
	private final FilmDirectorStorage filmDirectorStorage;

	public Film addFilm(Film film) {
		Film newFilm = filmStorage.addFilm(film);
		if (film.getGenres() != null) {
			filmGenreStorage.addGenreToFilm(film.getId(), film.getGenres());
		}
		newFilm.setGenres(filmGenreStorage.getGenresByFilmId(newFilm.getId()));
		return newFilm;
	}

	public Film updateFilm(Film film) {
		Film updatedFilm = filmStorage.updateFilm(film);
		if (film.getGenres() != null) {
			filmGenreStorage.addGenreToFilm(film.getId(), film.getGenres());
		}
		updatedFilm.setGenres(filmGenreStorage.getGenresByFilmId(updatedFilm.getId()));
		return updatedFilm;
	}

	public List<Film> findAllFilms() {
		return filmStorage.getListFilms();
	}

	public Film findFilmById(int id) {
		Film film = filmStorage.getFilmById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Фильма с id=%d нет", id)));
		film.setGenres(filmGenreStorage.getGenresByFilmId(film.getId()));
		return film;
	}

	public void deleteAllFilms() {
		filmStorage.deleteAllFilms();
	}

	public void deleteFilmById(int id) {
		filmStorage.deleteFilm(id);
	}

	public void addLike(int filmId, int userId) {
		checkingFilmInStorage(filmId);
		checkingUserInStorage(userId);
		likeStorage.addLike(filmId, userId);
	}

	public void deleteLike(int filmId, int userId) {
		checkingFilmInStorage(filmId);
		checkingUserInStorage(userId);
		likeStorage.deleteLike(filmId, userId);
	}

	public List<Film> getBestFilms(int count) {
		return likeStorage.getTopFilms(count).stream()
				.map(filmStorage.getFilmMap()::get)
				.collect(Collectors.toList());
	}

	public List<Film> getSortFilms(int idDirector, String typeSort) {
		return filmDirectorStorage.getSortFilms(idDirector,
				"year".equals(typeSort), "likes".equals(typeSort));
	}

	private void checkingFilmInStorage(int filmId) {
		if (!filmStorage.getFilmMap().containsKey(filmId)) {
			throw new NotFoundException(String.format("Фильма с id=%d нет", filmId));
		}
	}

	private void checkingUserInStorage(int userId) {
		if (!userStorage.getUserMap().containsKey(userId)) {
			throw new NotFoundException(String.format("Пользователя с id=%d нет", userId));
		}
	}
}

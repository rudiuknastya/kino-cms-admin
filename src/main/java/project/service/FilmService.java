package project.service;

import project.entity.Film;

import java.util.List;

public interface FilmService {
    List<Film>getAllFilms();
    Film getFilmById(Long id);
    Film saveFilm(Film film);
    void deleteFilmById(Long id);
    List<Film>getSoonFilms();
}

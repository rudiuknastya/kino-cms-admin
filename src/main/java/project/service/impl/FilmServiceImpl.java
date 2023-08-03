package project.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import project.entity.Film;
import project.repository.FilmRepository;
import project.service.FilmService;

import java.util.List;
@Service
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;

    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }
    private Logger logger = LogManager.getLogger("serviceLogger");
    @Override
    public List<Film> getAllFilms() {
        logger.info("getAllFilms() - Finding all films");
        List<Film> films = filmRepository.findAll();
        logger.info("getAllFilms() - Films were found");
        return films;
    }

    @Override
    public Film getFilmById(Long id) {
        logger.info("getFilmById() - Finding film by id "+id);
        Film film = filmRepository.findById(id).get();
        logger.info("getFilmById() - Film was found");
        return film;
    }

    @Override
    public Film saveFilm(Film film) {
        logger.info("saveFilm() - Saving film");
        Film film1 = filmRepository.save(film);
        logger.info("saveFilm() - Film was saved");
        return film1;
    }

    @Override
    public void deleteFilmById(Long id) {
        logger.info("deleteFilmById() - Deleting film by id "+id);
        filmRepository.deleteById(id);
        logger.info("deleteFilmById() - Film was deleted");
    }
}

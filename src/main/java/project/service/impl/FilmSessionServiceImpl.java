package project.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import project.dto.FilmSessionDTO;
import project.entity.FilmSession;
import project.repository.CriteriaFilmSessionRepository;
import project.repository.FilmSessionRepository;
import project.service.FilmSessionService;

import java.time.LocalDate;
import java.util.List;
@Service
public class FilmSessionServiceImpl implements FilmSessionService {
    private final FilmSessionRepository filmSessionRepository;
    private final CriteriaFilmSessionRepository criteriaFilmSessionRepository;

    public FilmSessionServiceImpl(FilmSessionRepository filmSessionRepository, CriteriaFilmSessionRepository criteriaFilmSessionRepository) {
        this.filmSessionRepository = filmSessionRepository;
        this.criteriaFilmSessionRepository = criteriaFilmSessionRepository;
    }

    private Logger logger = LogManager.getLogger("serviceLogger");

    @Override
    public FilmSession saveFilmSession(FilmSession filmSession) {
        logger.info("saveFilmSession() - Saving film session");
        FilmSession filmSession1 = filmSessionRepository.save(filmSession);
        logger.info("saveFilmSession() - Film session was saved");
        return filmSession1;
    }

    @Override
    public List<FilmSession> getFilmSessionsForToday() {
        logger.info("getFilmsToday() - Finding all film sessions for today");
        List<FilmSession> filmSessions = filmSessionRepository.filmsToday();
        logger.info("getFilmsToday() - All film sessions for today were found");
        return filmSessions;
    }

    @Override
    public FilmSession getFilmSessionById(Long id) {
        logger.info("getFilmSessionById() - Finding film session by id "+id);
        FilmSession filmSession = filmSessionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        logger.info("getFilmSessionById() - Film session was found");
        return filmSession;
    }

    @Override
    public List<FilmSession> getAllFilmSessions() {
        logger.info("getAllFilmSessions() - Finding all film sessions");
        List<FilmSession> filmSessions = filmSessionRepository.findAll();
        logger.info("getAllFilmSessions() - All film sessions were found");
        return filmSessions;
    }

    @Override
    public void deleteFilmSessionById(Long id) {
        logger.info("getAllFilmSessions() - Deleting film session by id "+id);
        filmSessionRepository.deleteById(id);
        logger.info("getAllFilmSessions() - Film sessions was deleted");
    }

    @Override
    public List<FilmSessionDTO> getFilmsForTodayForCinema(Long id) {
        logger.info("getFilmsForTodayForCinema() - Finding all films for today for cinema with id "+id);
        List<FilmSessionDTO> filmsForTodayForCinema = filmSessionRepository.filmsForTodayForCinema(id);
        logger.info("getFilmsForTodayForCinema() - All films were found");
        return filmsForTodayForCinema;
    }

    @Override
    public List<FilmSessionDTO> getFilmsForTodayForHall(Long id) {
        logger.info("getFilmsForTodayForHall() - Finding all films for today for hall with id "+id);
        List<FilmSessionDTO> filmsForTodayForHall = filmSessionRepository.filmsForTodayForHall(id);
        logger.info("getFilmsForTodayForHall() - All films were found");
        return filmsForTodayForHall;
    }

    @Override
    public List<FilmSession> getFilmSessionsForTomorrow() {
        logger.info("getFilmSessionsForTomorrow() - Finding all film sessions for tomorrow");
        List<FilmSession> filmSessionsForTomorrow = filmSessionRepository.filmsTomorrow();
        logger.info("getFilmSessionsForTomorrow() - All film sessions for tomorrow were found");
        return filmSessionsForTomorrow;
    }

    @Override
    public List<FilmSession> getFilmSessionsByCriteriaForToday(String [] types, String film) {
        logger.info("getFilmSessionsByCriteriaForToday() - Finding all film sessions by criteria for today");
        List<FilmSession> filmSessionsByCriteria = criteriaFilmSessionRepository.getSessionByCriteriaForToday(types, film);
        logger.info("getFilmSessionsByCriteriaForToday() - All film sessions by criteria for today were found");
        return filmSessionsByCriteria;
    }

    @Override
    public List<FilmSession> getFilmSessionsByCriteriaForTomorrow(String[] types, String film) {
        logger.info("getFilmSessionsByCriteriaForTomorrow() - Finding all film sessions by criteria for tomorrow");
        List<FilmSession> filmSessionsByCriteria = criteriaFilmSessionRepository.getSessionByCriteriaForTomorrow(types, film);
        logger.info("getFilmSessionsByCriteriaForTomorrow() - All film sessions by criteria for tomorrow were found");
        return filmSessionsByCriteria;
    }

    @Override
    public List<FilmSession> getFilmSessionsByCriteriaForDate(String[] types, String film, LocalDate sessionDate) {
        logger.info("getFilmSessionsByCriteriaForDate() - Finding all film sessions by criteria for date");
        List<FilmSession> filmSessionsByCriteria = criteriaFilmSessionRepository.getSessionByCriteriaForDate(types, film, sessionDate);
        logger.info("getFilmSessionsByCriteriaForDate() - All film sessions by criteria for date were found");
        return filmSessionsByCriteria;
    }

    @Override
    public List<FilmSession> getFilmSessionsForFilm(Long id) {
        logger.info("getFilmSessionsForFilm() - Finding all film sessions for film with id "+id);
        List<FilmSession> filmSessionsForFilm = filmSessionRepository.filmSessionsForFilm(id);
        logger.info("getFilmSessionsForFilm() - All film sessions for film were found");
        return filmSessionsForFilm;
    }
}

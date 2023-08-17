package project.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import project.dto.FilmSessionDTO;
import project.entity.FilmSession;
import project.repository.FilmSessionRepository;
import project.service.FilmSessionService;

import java.util.List;
@Service
public class FilmSessionServiceImpl implements FilmSessionService {
    private final FilmSessionRepository filmSessionRepository;

    public FilmSessionServiceImpl(FilmSessionRepository filmSessionRepository) {
        this.filmSessionRepository = filmSessionRepository;
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
    public List<FilmSession> getFilmsToday() {
        logger.info("getFilmsToday() - Finding all film sessions for today");
        List<FilmSession> filmSessions = filmSessionRepository.filmsToday();
        logger.info("getFilmsToday() - All film sessions for today were found");
        return filmSessions;
    }

    @Override
    public FilmSession getFilmSessionById(Long id) {
        logger.info("getFilmSessionById() - Finding film session by id "+id);
        FilmSession filmSession = filmSessionRepository.findById(id).get();
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
}

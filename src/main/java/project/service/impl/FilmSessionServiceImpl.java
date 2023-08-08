package project.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
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
}

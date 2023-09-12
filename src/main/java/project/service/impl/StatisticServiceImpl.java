package project.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import project.repository.CinemaRepository;
import project.repository.FilmRepository;
import project.repository.FilmSessionRepository;
import project.repository.UserRepository;
import project.service.StatisticService;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService {
    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    private final CinemaRepository cinemaRepository;
    private final FilmSessionRepository filmSessionRepository;

    public StatisticServiceImpl(UserRepository userRepository, FilmRepository filmRepository, CinemaRepository cinemaRepository, FilmSessionRepository filmSessionRepository) {
        this.userRepository = userRepository;
        this.filmRepository = filmRepository;
        this.cinemaRepository = cinemaRepository;
        this.filmSessionRepository = filmSessionRepository;
    }
    private Logger logger = LogManager.getLogger("serviceLogger");

    @Override
    public Long usersCount() {
        logger.info("usersCount() - Finding users count");
        Long count = userRepository.count();
        logger.info("usersCount() - Users count was found");
        return count;
    }

    @Override
    public Long filmsCount() {
        logger.info("usersCount() - Finding released films count");
        Long count = filmRepository.releasedFilmsCount();
        logger.info("usersCount() - Released films count was found");
        return count;
    }

    @Override
    public Long cinemasCount() {
        logger.info("cinemasCount() - Finding cinemas count");
        Long count = cinemaRepository.count();
        logger.info("cinemasCount() - Cinemas count was found");
        return count;
    }

    @Override
    public List<Long> getFilmSessionsInMonth() {
        logger.info("getFilmSessionsInMonth() - Finding film sessions for each month");
        List<Long> filmSessionsInMonth = filmSessionRepository.filmSessionsInMonth();
        logger.info("getFilmSessionsInMonth() - Film sessions for each month were found");
        return filmSessionsInMonth;
    }

    @Override
    public List<Long> getFilmSessionsPriceInMonth() {
        logger.info("getFilmSessionsPriceInMonth() - Finding film sessions prices for each month");
        List<Long> filmSessionsPriceInMonth = filmSessionRepository.filmSessionsPriceInMonth();
        logger.info("getFilmSessionsPriceInMonth() - Film sessions prices for each month were found");
        return filmSessionsPriceInMonth;
    }

    @Override
    public List<Long> getUsersLanguage() {
        logger.info("getUsersLanguage() - Finding users languages");
        List<Long> usersLanguage = new ArrayList<>();
        usersLanguage.add(userRepository.getUsersWithUkr());
        usersLanguage.add(userRepository.getUsersWithEng());
        logger.info("getUsersLanguage() - Users languages were found");
        return usersLanguage;
    }
}

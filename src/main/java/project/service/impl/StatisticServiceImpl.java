package project.service.impl;

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

    @Override
    public Long usersCount() {
        return userRepository.count();
    }

    @Override
    public Long filmsCount() {
        return filmRepository.releasedFilmsCount();
    }

    @Override
    public Long cinemasCount() {
        return cinemaRepository.count();
    }

    @Override
    public List<Long> getFilmSessionsInMonth() {
        List<Long> filmSessionsInMonth = filmSessionRepository.filmSessionsInMonth();
        return filmSessionsInMonth;
    }

    @Override
    public List<Long> getFilmSessionsPriceInMonth() {
        List<Long> filmSessionsPriceInMonth = filmSessionRepository.filmSessionsPriceInMonth();
        return filmSessionsPriceInMonth;
    }

    @Override
    public List<Long> getUsersLanguage() {
        List<Long> usersLanguage = new ArrayList<>();
        usersLanguage.add(userRepository.getUsersWithUkr());
        usersLanguage.add(userRepository.getUsersWithEng());
        return usersLanguage;
    }
}

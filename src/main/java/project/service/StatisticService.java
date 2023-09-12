package project.service;

import java.util.List;

public interface StatisticService {
    Long usersCount();
    Long filmsCount();
    Long cinemasCount();
    List<Long> getFilmSessionsInMonth();
    List<Long> getFilmSessionsPriceInMonth();
    List<Long> getUsersLanguage();

}

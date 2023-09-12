package project.service;

import project.dto.FilmSessionDTO;
import project.entity.FilmSession;

import java.time.LocalDate;
import java.util.List;

public interface FilmSessionService {
    FilmSession saveFilmSession(FilmSession filmSession);
    FilmSession getFilmSessionById(Long id);
    List<FilmSession> getAllFilmSessions();
    void deleteFilmSessionById(Long id);
    List<FilmSession> getFilmSessionsForToday();
    List<FilmSessionDTO> getFilmsForTodayForCinema(Long id);
    List<FilmSessionDTO> getFilmsForTodayForHall(Long id);
    List<FilmSession> getFilmSessionsForTomorrow();
    List<FilmSession> getFilmSessionsByCriteriaForToday(String []types, String film);
    List<FilmSession> getFilmSessionsByCriteriaForTomorrow(String []types, String film);
    List<FilmSession> getFilmSessionsByCriteriaForDate(String []types, String film, LocalDate sessionDate);
    List<FilmSession> getFilmSessionsForFilm(Long id);


}

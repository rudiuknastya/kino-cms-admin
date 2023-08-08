package project.service;

import project.entity.FilmSession;

import java.util.List;

public interface FilmSessionService {
    FilmSession saveFilmSession(FilmSession filmSession);
    FilmSession getFilmSessionById(Long id);
    List<FilmSession> getAllFilmSessions();
    void deleteFilmSessionById(Long id);

}

package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.dto.FilmSessionDTO;
import project.entity.FilmSession;

import java.util.List;

public interface FilmSessionRepository extends JpaRepository<FilmSession, Long> {
    @Query(value = "SELECT COUNT(session_date) FROM film_session where year(session_date) = year(now()) group by month(session_date) order by month(session_date)", nativeQuery = true)
    List<Long> filmSessionsInMonth();

    @Query(value = "select sum(price) session_date from film_session where year(session_date) = year(now()) group by month(session_date) order by month(session_date)", nativeQuery = true)
    List<Long> filmSessionsPriceInMonth();
    @Query(value = "SELECT * FROM film_session where session_date = date(now())", nativeQuery = true)
    List<FilmSession> filmsToday();
    @Query(value = "select name as filmName, film_session.id as filmSessionId from film_session inner join hall on film_session.hall_id = hall.id inner join film on film_session.film_id = film.id where cinema_id= :cinemaId and session_date = date(now())", nativeQuery = true)
    List<FilmSessionDTO> filmsForTodayForCinema(@Param("cinemaId")Long id);
    @Query(value = "select name as filmName, film_session.id as filmSessionId from film_session inner join hall on film_session.hall_id = hall.id inner join film on film_session.film_id = film.id where hall_id= :hallId and session_date = date(now())", nativeQuery = true)
    List<FilmSessionDTO> filmsForTodayForHall(@Param("hallId")Long id);

    @Query(value = "SELECT * FROM film_session WHERE session_date = DATE_ADD(CURDATE(),INTERVAL 1 DAY);", nativeQuery = true)
    List<FilmSession> filmsTomorrow();
    @Query(value = "SELECT * FROM film_session where film_id = :filmId and session_date > date(now()) LIMIT 6;", nativeQuery = true)
    List<FilmSession> filmSessionsForFilm(@Param("filmId")Long id);

}

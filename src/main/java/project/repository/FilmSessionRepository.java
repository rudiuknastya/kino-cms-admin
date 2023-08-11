package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.entity.FilmSession;

public interface FilmSessionRepository extends JpaRepository<FilmSession, Long> {
    @Query(value = "SELECT COUNT(session_date) FROM film_session WHERE month(session_date)= ?1", nativeQuery = true)
    Long filmSessionsInMonth(Integer monthNumber);

    @Query(value = "SELECT SUM(price) FROM film_session WHERE month(session_date)= ?1", nativeQuery = true)
    Long filmSessionsPriceInMonth(Integer monthNumber);

}

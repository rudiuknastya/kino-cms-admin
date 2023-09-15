package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.dto.FilmSessionDTO;
import project.entity.FilmSession;

import java.util.List;

public interface FilmSessionRepository extends JpaRepository<FilmSession, Long> {
    @Query(value = "SELECT ifnull(count(id),0) AS total_num\n" +
            "from (select STR_TO_DATE('01/01/2016', '%d/%m/%Y') as month_date union \n" +
            " select STR_TO_DATE('01/02/2016', '%d/%m/%Y') as month_date union \n" +
            " select STR_TO_DATE('01/03/2016', '%d/%m/%Y') as month_date union \n" +
            " select STR_TO_DATE('01/04/2016', '%d/%m/%Y') as month_date union \n" +
            " select STR_TO_DATE('01/05/2016', '%d/%m/%Y') as month_date union \n" +
            " select STR_TO_DATE('01/06/2016', '%d/%m/%Y') as month_date union \n" +
            " select STR_TO_DATE('01/07/2016', '%d/%m/%Y') as month_date union \n" +
            " select STR_TO_DATE('01/08/2016', '%d/%m/%Y') as month_date union \n" +
            " select STR_TO_DATE('01/09/2016', '%d/%m/%Y') as month_date union \n" +
            " select STR_TO_DATE('01/10/2016', '%d/%m/%Y') as month_date union \n" +
            " select STR_TO_DATE('01/11/2016', '%d/%m/%Y') as month_date union \n" +
            " select STR_TO_DATE('01/12/2016', '%d/%m/%Y') as month_date) m\n" +
            "left join film_session f\n" +
            "on month(m.month_date) = month(f.session_date)\n" +
            "GROUP BY month_date;", nativeQuery = true)
    List<Long> filmSessionsInMonth();

    @Query(value = "SELECT ifnull(avg(price),0) AS total_num\n" +
            "from (select STR_TO_DATE('01/01/2016', '%d/%m/%Y') as month_date union \n" +
            " select STR_TO_DATE('01/02/2016', '%d/%m/%Y') as month_date union \n" +
            " select STR_TO_DATE('01/03/2016', '%d/%m/%Y') as month_date union \n" +
            " select STR_TO_DATE('01/04/2016', '%d/%m/%Y') as month_date union \n" +
            " select STR_TO_DATE('01/05/2016', '%d/%m/%Y') as month_date union \n" +
            " select STR_TO_DATE('01/06/2016', '%d/%m/%Y') as month_date union \n" +
            " select STR_TO_DATE('01/07/2016', '%d/%m/%Y') as month_date union \n" +
            " select STR_TO_DATE('01/08/2016', '%d/%m/%Y') as month_date union \n" +
            " select STR_TO_DATE('01/09/2016', '%d/%m/%Y') as month_date union \n" +
            " select STR_TO_DATE('01/10/2016', '%d/%m/%Y') as month_date union \n" +
            " select STR_TO_DATE('01/11/2016', '%d/%m/%Y') as month_date union \n" +
            " select STR_TO_DATE('01/12/2016', '%d/%m/%Y') as month_date) m\n" +
            "left join film_session f\n" +
            "on month(m.month_date) = month(f.session_date)\n" +
            "GROUP BY month_date", nativeQuery = true)
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

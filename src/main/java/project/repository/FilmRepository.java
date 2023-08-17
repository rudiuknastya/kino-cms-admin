package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.entity.Film;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {
    @Query(value = "SELECT COUNT(id) FROM film WHERE date < now()", nativeQuery = true)
    Long releasedFilmsCount();

    @Query(value = "select * from film where date > now()", nativeQuery = true)
    List<Film> soonFilms();

}

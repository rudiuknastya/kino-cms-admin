package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.Film;

public interface FilmRepository extends JpaRepository<Film, Long> {
}

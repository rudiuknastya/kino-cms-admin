package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.FilmSession;

public interface FilmSessionRepository extends JpaRepository<FilmSession, Long> {
}

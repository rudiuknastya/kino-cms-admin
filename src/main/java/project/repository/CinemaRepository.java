package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.Cinema;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {
}

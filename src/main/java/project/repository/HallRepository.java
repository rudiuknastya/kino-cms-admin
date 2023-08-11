package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.Hall;

public interface HallRepository extends JpaRepository<Hall, Long> {
}

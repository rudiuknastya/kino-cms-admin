package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.Cafe;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
}

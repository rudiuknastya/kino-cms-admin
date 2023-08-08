package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.NewPage;

public interface NewPageRepository extends JpaRepository<NewPage, Long> {
}

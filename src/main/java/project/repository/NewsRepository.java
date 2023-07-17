package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.News;

public interface NewsRepository extends JpaRepository<News, Long> {
}

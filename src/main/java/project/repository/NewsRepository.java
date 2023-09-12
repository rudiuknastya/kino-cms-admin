package project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.News;

public interface NewsRepository extends JpaRepository<News, Long> {
    Page<News> findByStatusNot(Boolean status, Pageable pageable);

}

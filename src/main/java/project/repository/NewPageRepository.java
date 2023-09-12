package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.entity.NewPage;

import java.util.List;

public interface NewPageRepository extends JpaRepository<NewPage, Long> {
    @Query(value = "SELECT * FROM new_page where status = 1", nativeQuery = true)
    List<NewPage> enabledNewPages();
}

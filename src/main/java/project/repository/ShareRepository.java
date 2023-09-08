package project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.Share;

public interface ShareRepository extends JpaRepository<Share,Long> {
    Page<Share> findByStatusNot(Boolean status, Pageable pageable);

}

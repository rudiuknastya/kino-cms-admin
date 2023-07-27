package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.Share;

public interface ShareRepository extends JpaRepository<Share,Long> {

}

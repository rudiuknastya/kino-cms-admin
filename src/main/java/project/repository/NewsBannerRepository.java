package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.NewsBanner;

public interface NewsBannerRepository extends JpaRepository<NewsBanner, Long> {
}

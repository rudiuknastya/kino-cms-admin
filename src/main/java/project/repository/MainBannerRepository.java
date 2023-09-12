package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.MainBanner;

public interface MainBannerRepository extends JpaRepository<MainBanner, Long> {
}

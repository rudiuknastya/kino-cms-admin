package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.Advertisement;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
}

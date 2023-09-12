package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.BackgroundImage;

public interface BackgroundImageRepository extends JpaRepository<BackgroundImage,Long> {
}

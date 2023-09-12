package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.MailFile;

public interface MailFilesRepository extends JpaRepository<MailFile, Long> {
}

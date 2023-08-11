package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.MailFiles;

public interface MailFilesRepository extends JpaRepository<MailFiles, Long> {
}

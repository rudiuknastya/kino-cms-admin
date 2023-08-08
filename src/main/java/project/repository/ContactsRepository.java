package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.Contacts;

public interface ContactsRepository extends JpaRepository<Contacts, Long> {
}

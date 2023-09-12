package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.Contact;

public interface ContactsRepository extends JpaRepository<Contact, Long> {
}

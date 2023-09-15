package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.Contact;

import java.util.List;

public interface ContactsRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByStatusNot(Boolean status);
}

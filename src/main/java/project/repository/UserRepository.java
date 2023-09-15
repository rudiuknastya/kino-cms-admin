package project.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT COUNT(id) FROM users WHERE language = 'ukr' and role='USER'", nativeQuery = true)
    Long getUsersWithUkr();
    @Query(value = "SELECT COUNT(id) FROM users WHERE language = 'eng' and role='USER'", nativeQuery = true)
    Long getUsersWithEng();
    @Query(value = "SELECT COUNT(id) FROM users WHERE role='USER'", nativeQuery = true)
    Long getUsersCount();
    Optional<User> findByEmail(String email);
}

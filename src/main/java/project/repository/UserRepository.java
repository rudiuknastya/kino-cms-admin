package project.repository;

import org.springframework.data.repository.CrudRepository;
import project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

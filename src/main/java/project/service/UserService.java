package project.service;

import project.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User updateUser(User user);
    void deleteUserById(Long id);
    Long getUsersCount();
}

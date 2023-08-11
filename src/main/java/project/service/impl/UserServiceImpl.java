package project.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import project.entity.User;
import project.service.UserService;
import org.springframework.stereotype.Service;
import project.repository.UserRepository;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    private Logger logger = LogManager.getLogger("serviceLogger");

    @Override
    public List<User> getAllUsers() {
        logger.info("getAllUsers() - Finding all users");
        List<User> users = userRepository.findAll();
        logger.info("getAllUsers() - All users were found");
        return users;
    }

    @Override
    public User updateUser(User user) {
        logger.info("updateUser() - Updating user");
        User userSaved = userRepository.save(user);
        logger.info("updateUser() - User was updated");
        return userSaved;
    }

    @Override
    public User getUserById(Long id) {
        logger.info("getUserById() - Finding user by id "+id);
        User foundUser = userRepository.findById(id).get();
        logger.info("getUserById() - User was found");
        return foundUser;
    }

    @Override
    public void deleteUserById(Long id) {
        logger.info("deleteUserById() - Deleting user by id "+id);
        userRepository.deleteById(id);
        logger.info("getUserById() - User was deleted");
    }

    @Override
    public Long getUsersCount() {
        logger.info("getUsersCount() - Getting users count");
        Long usersCount = userRepository.count();
        logger.info("getUsersCount() - Got users count");
        return usersCount;
    }
}

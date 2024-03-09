package tech.ada.extends_insights.service;

import org.springframework.stereotype.Service;
import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        userRepository.save(user);
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User changePassword(Long id, String newPassword) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setPassword(newPassword);
            userRepository.save(user);
        }
        return user;
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}

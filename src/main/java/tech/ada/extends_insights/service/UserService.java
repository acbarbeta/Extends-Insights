package tech.ada.extends_insights.service;

import org.springframework.http.ResponseEntity;
import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.domain.models.requests.ChangePasswordRequest;
import tech.ada.extends_insights.domain.models.requests.UserRequest;

import java.util.List;

public interface UserService {
    User registerUser(UserRequest request);
    List<User> findAllUsers();
    User getUserById(Long id);
    User getUserByUsername(String username);
    ResponseEntity<String> changePassword(Long id, ChangePasswordRequest changePasswordRequest);
    void deleteUserById(Long id);
}

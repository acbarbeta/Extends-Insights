package tech.ada.extends_insights.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.domain.models.requests.ChangePasswordRequest;
import tech.ada.extends_insights.domain.models.requests.UserRequest;
import tech.ada.extends_insights.repository.UserRepository;
import tech.ada.extends_insights.service.UserService;
import tech.ada.extends_insights.service.exceptions.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public User registerUser(UserRequest request) {
        User convertedUser = modelMapper.map(request, User.class);
        return userRepository.save(convertedUser);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(() -> new ObjectNotFoundException("User not found"));
    }

    @Override
    public User getUserByUsername(String username) {
        String userNameNoSpace = username.replaceAll("\\s", "");
        return userRepository.findByUsername(userNameNoSpace);
    }

    @Override
    public ResponseEntity<String> changePassword(Long id, ChangePasswordRequest changePasswordRequest) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(changePasswordRequest.getNewPassword());
            userRepository.save(user);
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public void deleteUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
            ResponseEntity.noContent().build();
        } else {
            ResponseEntity.notFound().build();
        }
    }
}
package tech.ada.extends_insights.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.domain.models.requests.ChangePasswordRequest;
import tech.ada.extends_insights.domain.models.requests.UserRequest;
import tech.ada.extends_insights.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController("/users")
public class UserController {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserRepository userRepository, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/users")
    public ResponseEntity<User> registerUser(@RequestBody UserRequest request){
        User convertedUser = modelMapper.map(request, User.class);
        User newUser = userRepository.save(convertedUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("/users/searchAll")
    public List<User> findAllUsers(){
        List<User> registeredUsers = userRepository.findAll();
        return registeredUsers;
    }

    @GetMapping(value = "users", params = {"username"})
    public User getUserByUsername(@RequestParam String username){
        String userNameNoSpace = username.replaceAll("\\s","");
        return userRepository.findByUsername(userNameNoSpace);
    }

    @PatchMapping("/users/{id}/password")
    public ResponseEntity<String> changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setPassword(changePasswordRequest.getNewPassword());
        userRepository.save(user);

        User updatedUser = userRepository.findById(id).orElse(null);

        return ResponseEntity.ok("Password updated successfully");
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);

        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }
}
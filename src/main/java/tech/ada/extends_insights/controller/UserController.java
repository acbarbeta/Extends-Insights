package tech.ada.extends_insights.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.domain.models.requests.UserRequest;
import tech.ada.extends_insights.repository.UserRepository;

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


    @GetMapping(value = "users", params = {"username"})
    public User getUserByUsername(@RequestParam String username){
        String userNameNoSpace = username.replaceAll("\\s","");
        return userRepository.findByUsername(userNameNoSpace);
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
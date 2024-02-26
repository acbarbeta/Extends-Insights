package tech.ada.extends_insights.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

@RestController("/users")
public class UserController {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserRepository userRepository, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/users")
    public ResponseEntity<User> registerUser(@RequestBody UserRequest request){
        User convertedUser = modelMapper.map(request, User.class);
        User newUser = userRepository.save(convertedUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found"),
            @ApiResponse(responseCode = "404", description = "Users not found")
    })
    @GetMapping("/users/searchAll")
    public List<User> findAllUsers(){
        List<User> registeredUsers = userRepository.findAll();
        return registeredUsers;
    }

    @Operation(summary = "Get user by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping(value = "users", params = {"username"})
    public User getUserByUsername(@RequestParam String username){
        String userNameNoSpace = username.replaceAll("\\s","");
        return userRepository.findByUsername(userNameNoSpace);
    }

    @Operation(summary = "Change user password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password updated"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PatchMapping("/users/{id}/password")
    public ResponseEntity<String> changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setPassword(changePasswordRequest.getNewPassword());
        userRepository.save(user);

        return ResponseEntity.ok("Password updated successfully");
    }

    @Operation(summary = "Delete user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
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
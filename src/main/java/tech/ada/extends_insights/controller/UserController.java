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
import tech.ada.extends_insights.service.UserService;

import java.util.List;

@RestController("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper){
        this.userService = userService;
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
        User newUser = userService.registerUser(convertedUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found"),
            @ApiResponse(responseCode = "404", description = "Users not found")
    })
    @GetMapping("/users/searchAll")
    public List<User> findAllUsers(){
        List<User> registeredUsers = userService.getAllUsers();
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
        return userService.getUserByUsername(userNameNoSpace);
    }

    @Operation(summary = "Change user password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password updated"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{id}/password")
    public ResponseEntity<String> changePassword(@PathVariable Long id, @RequestBody String newPassword) {
        try {
            User user = userService.getUserById(id);

            if (user == null) {
                return new ResponseEntity<>("Usuário não encontrado", HttpStatus.NOT_FOUND);
            }

            user.setPassword(newPassword);
            userService.changePassword(id, newPassword);

            return new ResponseEntity<>("Senha alterada com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao alterar a senha", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);

        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
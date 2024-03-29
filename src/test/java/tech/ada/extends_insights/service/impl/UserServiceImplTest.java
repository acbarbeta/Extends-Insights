package tech.ada.extends_insights.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.domain.models.requests.ChangePasswordRequest;
import tech.ada.extends_insights.domain.models.requests.UserRequest;
import tech.ada.extends_insights.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    public static final long USER_ID = 1L;
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email@email.com";
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;

    private User user;
    private UserRequest userRequest;
    private Optional<User> optionalUser;
    private ChangePasswordRequest changePasswordRequest;
    private List<User> userList;


    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, modelMapper);
        user = new User(USERNAME, PASSWORD, EMAIL);
        optionalUser = Optional.of(user);
        userRequest = new UserRequest(USERNAME, PASSWORD, EMAIL);
        changePasswordRequest = new ChangePasswordRequest(PASSWORD);
        userList = List.of(user);
    }

    @DisplayName("Should register a new user successfully")
    @Test
    void registerUser() {
        Mockito.when(modelMapper.map(any(), any())).thenReturn(user);
        Mockito.when(userRepository.save(any())).thenReturn(user);
        User registerUser = userService.registerUser(userRequest);
        assertEquals(USERNAME, registerUser.getUsername());
    }

    @Test
    void findAllUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(userList);
        List<User> allUsers = userService.findAllUsers();
        assertEquals(userList, allUsers);
    }

    @Test
    void getUserById() {

        Mockito.when(userRepository.findById(USER_ID)).thenReturn(optionalUser);
        User userById = userService.getUserById(USER_ID);
        assertEquals(userById, user);
    }
    @Test
    void whenFindByIdThenReturnUserNotFound() {
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());
        Assertions.assertThrows(Exception.class, () -> userService.getUserById(USER_ID));
    }

    @Test
    void whenFindUserByUsernameThenReturnAnInstance() {
        Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(user);
        User userByUsername = userService.getUserByUsername(USERNAME);
        assertEquals(userByUsername, user);
    }

    @Test
    void changePassword() {
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(optionalUser);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        String changePassword = userService.changePassword(USER_ID, changePasswordRequest);
        assertEquals(changePassword, "Password changed successfully");
    }

    @Test
    void deleteUserById() {
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(optionalUser);
        userService.deleteUserById(USER_ID);
        Mockito.verify(userRepository).delete(user);
    }
}
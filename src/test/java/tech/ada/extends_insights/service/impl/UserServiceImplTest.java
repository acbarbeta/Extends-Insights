package tech.ada.extends_insights.service.impl;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.domain.models.requests.ChangePasswordRequest;
import tech.ada.extends_insights.domain.models.requests.UserRequest;
import tech.ada.extends_insights.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class UserServiceImplTest {

    public static final long USER_ID = 1L;
    public static final String USERNAME = "user";
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

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    void setUp() {
        user = new User(USERNAME, PASSWORD, EMAIL);
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @DisplayName("Should register a new user successfully")
    @Test
    void registerUser() {
        Mockito.when(modelMapper.map(userRequest, User.class)).thenReturn(user);
        Mockito.when(userRepository.save(any())).thenReturn(user);
        User registerUser = userService.registerUser(userRequest);
        assertEquals(USERNAME, registerUser.getUsername());
    }

    @Test
    void findAllUsers() {
        UserRequest userRequest = new UserRequest(USERNAME, PASSWORD, EMAIL);
        this.createUser(userRequest);

        List<User> foundedUser = this.userRepository.findAll();
        Assertions.assertTrue(foundedUser.size() > 0);
//        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));
//        List<User> allUsers = userService.findAllUsers();
//        Assertions.assertEquals(allUsers, List.of(user));
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

    private void startUser() {
        user = new User(USER_ID, USERNAME, PASSWORD, EMAIL);

        userRequest = new UserRequest(USERNAME, PASSWORD, EMAIL);

        optionalUser = Optional.of(new User(USER_ID, USERNAME, PASSWORD, EMAIL));

        changePasswordRequest = new ChangePasswordRequest(PASSWORD);
    }

    private  User createUser(UserRequest userRequest) {
        User newUser = new User();
        this.entityManager.persist(newUser);
        return newUser;
    }
}
package tech.ada.extends_insights.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.repository.UserRepository;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserRepository userRepository;
    private User user;
    private List<User> userList;

    @InjectMocks
    private UserController userController;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        user = new User("usernametest","123456789","email@test.com");
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    public static String asJsonString(final Object object){
        try{
            return new ObjectMapper().writeValueAsString(object);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void registerUserHttpTest() throws Exception{
        when(userRepository.save(Mockito.any())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/users").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(user))).andExpect(status().isCreated());

        verify(userRepository, times(1)).save(Mockito.any());
    }

    @Test
    void findAllUsers() {
    }

    @Test
    void getUserByUsername() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void deleteUserById() {
    }
}
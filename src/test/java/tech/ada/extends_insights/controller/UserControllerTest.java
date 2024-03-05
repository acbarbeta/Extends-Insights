package tech.ada.extends_insights.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserController userController;

    private User user;
    private List<User> userList;

    @BeforeEach
    public void setup() {
        user = new User("usernametest", "123456789", "email@test.com");
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void registerUserHttpTest() throws Exception {
        when(modelMapper.map(any(), any())).thenReturn(user);

        when(userRepository.save(any())).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isCreated());

        verify(userRepository, times(1)).save(any());
    }


    @Test
    void findAllUsers() {
    }

    @Test
    void getUserByUsername() {
    }

    void changePassword() throws Exception {
        Long id = 1L;
        String newPassword = user.getPassword();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        user.setPassword(newPassword);
        when(userRepository.save(any())).thenReturn(user);

        mockMvc.perform(patch("/users/{id}/password", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newPassword)))
                .andExpect(status().isOk());

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void deleteUserByIdTest() throws Exception {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        mockMvc.perform(delete("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(userRepository).delete(user);
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static String asJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

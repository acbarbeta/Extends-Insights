package tech.ada.extends_insights.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import tech.ada.extends_insights.domain.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.ada.extends_insights.service.UserService;

import java.util.List;

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
    private UserService userService;

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
        when(userService.registerUser(any())).thenReturn(user);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isCreated());
        verify(userService, times(1)).registerUser(any());
    }

    @Test
    void findAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(userList);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/searchAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andDo(MockMvcResultHandlers.print());
        verify(userService).getAllUsers();
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getUserByUsername() throws Exception {
        String username = user.getUsername();
        when(userService.getUserByUsername(username)).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.get("/users?username=" + username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andDo(MockMvcResultHandlers.print());
        verify(userService).getUserByUsername(username);
        verify(userService, times(1)).getUserByUsername(username);
    }

//    @Test
//    void changePassword() throws Exception {
//        Long id = user.getId();
//        String newPassword = user.getPassword();
//
//        when(userService.getUserById(id)).thenReturn(user);
//
//        user.setPassword(newPassword);
//        when(userService.registerUser(any())).thenReturn(user);
//
//        mockMvc.perform(patch("/users/{id}/password", id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(newPassword)))
//                .andExpect(status().isOk());
//
//        verify(userService, times(1)).getUserById(id);
//        verify(userService, times(1)).registerUser(any());
//    }

    @Test
    void changePassword() throws Exception {

        User user = new User();
        user.setPassword("senhaAntiga");

        when(userService.getUserById(anyLong())).thenReturn(user);
        when(userService.registerUser(any())).thenReturn(user);

        mockMvc.perform(patch("/users/{id}/password", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString("novaSenha")))
                .andExpect(status().isOk());

        verify(userService, times(1)).getUserById(anyLong());
        verify(userService, times(1)).registerUser(any());
    }

    @Test
    public void deleteUserByIdTest() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(user);
        mockMvc.perform(delete("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(userService).deleteUserById(anyLong());
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

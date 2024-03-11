package tech.ada.extends_insights.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.domain.models.requests.ChangePasswordRequest;
import tech.ada.extends_insights.service.impl.UserServiceImpl;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserController userController;

    private User user;
    private List<User> userList;
    private ChangePasswordRequest changePasswordRequest;

    @BeforeEach
    public void setup() {
        user = new User("usernameTest", "123456789", "email@test.com");
        userList = List.of(user);
        changePasswordRequest = new ChangePasswordRequest("987654321");
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void registerUserHttpTest() throws Exception {
        when(userService.registerUser(any())).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isCreated());
        verify(userService, times(1)).registerUser(any());
    }

    @Test
    void findAllUsersHttpTest() throws Exception {
        when(userService.findAllUsers()).thenReturn(userList);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/searchAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andDo(MockMvcResultHandlers.print());
        verify(userService).findAllUsers();
        verify(userService, times(1)).findAllUsers();
    }

    @Test
    void getUserByIdHttpTest() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + anyLong())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andDo(MockMvcResultHandlers.print());
        verify(userService).getUserById(anyLong());
        verify(userService, times(1)).getUserById(anyLong());
    }

    @Test
    void getUserByUsernameHttpTest() throws Exception {
        when(userService.getUserByUsername(anyString())).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/users?username=" + anyString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andDo(MockMvcResultHandlers.print());
        verify(userService).getUserByUsername(anyString());
        verify(userService, times(1)).getUserByUsername(anyString());
    }

    @Test
    void changePasswordHttpTest() throws Exception {
        when(userService.changePassword(anyLong(), any())).thenReturn("Password changed successfully");
        mockMvc.perform(MockMvcRequestBuilders.patch("/users/{id}/password", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(changePasswordRequest)))
                .andExpect(status().isOk());
        verify(userService, times(1)).changePassword(anyLong(), any());
    }

    @Test
    public void deleteUserByIdHttpTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", anyLong())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isNoContent());
        verify(userService, times(1)).deleteUserById(anyLong());
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

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
import tech.ada.extends_insights.domain.entities.Comment;
import tech.ada.extends_insights.domain.entities.Publication;
import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.domain.models.requests.CommentRequest;
import tech.ada.extends_insights.domain.models.requests.UpdateCommentRequest;
import tech.ada.extends_insights.service.impl.CommentServiceImpl;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.ada.extends_insights.service.impl.CommentServiceImplTest.*;
import static tech.ada.extends_insights.service.impl.UserServiceImplTest.*;

@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CommentServiceImpl commentService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CommentController commentController;

    private Comment comment;
    private List<Comment> commentList;
    private CommentRequest commentRequest;
    private UpdateCommentRequest updateCommentRequest;
    private User user;
    private Publication publication;

    @BeforeEach
    public void setup() {
        comment = new Comment(COMMENT_ID, user, publication, COMMENT_CONTENT);
        commentList = List.of(comment);
        commentRequest = new CommentRequest(user, PUBLICATION_ID, COMMENT_CONTENT);
        updateCommentRequest = new UpdateCommentRequest(COMMENT_CONTENT);
        publication = new Publication();
        user = new User(USERNAME, PASSWORD, EMAIL);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }
    @Test
    public void createComment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/comments/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(commentRequest)))
                .andExpect(status().isCreated());
        verify(commentService, times(1)).createComment(any());
    }

    @Test
    public void getAllComments() throws Exception {
        when(commentService.getAllComments()).thenReturn(commentList);
        mockMvc.perform(MockMvcRequestBuilders.get("/comments/all-comments")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.[0].commentBody", equalTo(COMMENT_CONTENT)));
    }

//    @Test
//    void getCommentByUserId() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/comments/{author}" + user.getUserId())
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(jsonPath("$.[0].commentBody", equalTo(COMMENT_CONTENT)));
//
//    }

    @Test
    void updateComment() throws Exception {
        when(commentService.updateComment(anyLong(), any())).thenReturn(String.valueOf(comment));
        mockMvc.perform(MockMvcRequestBuilders.patch("/comments/comments/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updateCommentRequest)))
                .andExpect(status().isOk());
        verify(commentService, times(1)).updateComment(anyLong(), any());

    }

    @Test
    void deleteComment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/comments/comments/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(commentService, times(1)).deleteComment(anyLong());
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
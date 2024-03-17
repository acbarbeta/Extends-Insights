package tech.ada.extends_insights.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import tech.ada.extends_insights.domain.entities.Comment;
import tech.ada.extends_insights.domain.entities.Publication;
import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.domain.models.requests.CommentRequest;
import tech.ada.extends_insights.domain.models.requests.UpdateCommentRequest;
import tech.ada.extends_insights.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

    public static final long COMMENT_ID = 1L;
    public static final String COMMENT_CONTENT = "commentContent";
    public static final long PUBLICATION_ID = 1L;

    @InjectMocks
    private CommentServiceImpl commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ModelMapper modelMapper;

    private Comment comment;
    private CommentRequest commentRequest;
    private Optional<Comment> optionalComment;
    private UpdateCommentRequest updateCommentRequest;
    private List<Comment> commentList;
    private User user;
    private Publication publication;

    @BeforeEach
    void setUp() {
        commentService = new CommentServiceImpl(commentRepository, modelMapper);
        comment = new Comment(COMMENT_ID, user, publication, COMMENT_CONTENT);
        optionalComment = Optional.of(comment);
        commentRequest = new CommentRequest(user, PUBLICATION_ID, COMMENT_CONTENT);
        updateCommentRequest = new UpdateCommentRequest("newCommentContent");
        commentList = List.of(comment);
        user = new User();
        publication = new Publication();
    }

    @DisplayName("Should create a new comment successfully")
    @Test
    void createComment() {
        Mockito.when(modelMapper.map(commentRequest, Comment.class)).thenReturn(comment);
        Mockito.when(commentRepository.save(comment)).thenReturn(comment);
        Comment comment = commentService.createComment(commentRequest);
        assertEquals(COMMENT_CONTENT, comment.getCommentBody());
    }

    @Test
    void getAllComments() {
        Mockito.when(commentRepository.findAll()).thenReturn(commentList);
        List<Comment> comments = commentService.getAllComments();
        assertEquals(commentList, comments);
    }

    @Test
    void getCommentByUserId() {
        Mockito.when(commentRepository.findByAuthorId(user.getUserId())).thenReturn(commentList);
        List<Comment> comments = commentService.getCommentByUserId(user.getUserId());
        assertEquals(commentList, comments);
    }

    @Test
    void updateComment() {
        Mockito.when(commentRepository.findById(COMMENT_ID)).thenReturn(optionalComment);
        Mockito.when(commentRepository.save(comment)).thenReturn(comment);
        String updateComment = commentService.updateComment(COMMENT_ID, updateCommentRequest);
        assertEquals("Comment updated successfully", updateComment);
    }

    @Test
    void deleteComment() {
        Mockito.when(commentRepository.findById(COMMENT_ID)).thenReturn(optionalComment);
        commentService.deleteComment(COMMENT_ID);
        Mockito.verify(commentRepository).delete(comment);
    }
}
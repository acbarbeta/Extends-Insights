package tech.ada.extends_insights.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.extends_insights.domain.entities.Comment;
import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.domain.models.requests.CommentRequest;
import tech.ada.extends_insights.domain.models.requests.UpdateCommentRequest;
import tech.ada.extends_insights.repository.CommentRepository;
import java.util.Optional;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentController(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Create a new comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/comments")
    public ResponseEntity<Comment> createComment(@RequestBody CommentRequest commentRequest) {
        Comment convertedcomment = modelMapper.map(commentRequest, Comment.class);
        Comment newComment = commentRepository.save(convertedcomment);
        return ResponseEntity.status(HttpStatus.CREATED).body(newComment);
    }

    @Operation(summary = "Get all comments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments found"),
            @ApiResponse(responseCode = "404", description = "Comments not found")
    })
    @GetMapping("/all-comments")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Get comment by author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment found"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @GetMapping(value = "/comments", params = {"authorId"})
    public ResponseEntity<List<Comment>> getCommentByUserId(@RequestParam User authorId) {
        List<Comment> comments = commentRepository.findByAuthor(authorId);
        if(comments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Update comment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment found"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @PatchMapping("/comments/{id}")
    public ResponseEntity<String> updateComment(@PathVariable Long id, @RequestBody UpdateCommentRequest commentRequest) {

        Comment comment = commentRepository.findById(id).orElse(null);

        if (comment == null) {
            return ResponseEntity.notFound().build();
        }

        comment.setCommentBody(commentRequest.getNewCommentBody());
        commentRepository.save(comment);

        return ResponseEntity.ok("Comment updated successfully");

    }

    @Operation(summary = "Delete comment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comment deleted"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        commentRepository.delete(commentOptional.get());
        return ResponseEntity.noContent().build();
    }
}
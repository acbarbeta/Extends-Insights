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
import tech.ada.extends_insights.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Create a new comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/comments")
    public ResponseEntity<Comment> createComment(@RequestBody CommentRequest commentRequest) {
        Comment newComment = commentService.createComment(commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newComment);
    }

    @Operation(summary = "Get all comments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments found"),
            @ApiResponse(responseCode = "404", description = "Comments not found")
    })
    @GetMapping("/all-comments")
    public ResponseEntity<List<Comment>> getAllComments() {
        return ResponseEntity.ok().body(commentService.getAllComments());
    }

    @Operation(summary = "Get comment by author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment found"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @GetMapping("/get-comment-by-authorId/{authorId}")
    public ResponseEntity<List<Comment>> getCommentByUserId(@PathVariable Long authorId) {
        return ResponseEntity.ok().body(commentService.getCommentByUserId(authorId));
    }

    @Operation(summary = "Update comment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment found"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @PatchMapping("/comments/{id}")
    public ResponseEntity<String> updateComment(@PathVariable Long id, @RequestBody UpdateCommentRequest updateCommentRequest) {
        return ResponseEntity.ok().body(commentService.updateComment(id, updateCommentRequest));
    }

    @Operation(summary = "Delete comment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comment deleted"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {

        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
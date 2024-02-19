package tech.ada.extends_insights.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.extends_insights.domain.entities.Comment;
import tech.ada.extends_insights.domain.models.requests.CommentRequest;
import tech.ada.extends_insights.repository.CommentRepository;
import java.util.Optional;

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

    @PostMapping("/comments")
    public ResponseEntity<Comment> createComment(@RequestBody CommentRequest commentRequest) {
        Comment convertedcomment = modelMapper.map(commentRequest, Comment.class);
        Comment newComment = commentRepository.save(convertedcomment);
        return ResponseEntity.status(HttpStatus.CREATED).body(newComment);
    }









    @PutMapping("/comments/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Comment existingComment = optionalComment.get();
        modelMapper.map(commentRequest, existingComment);

        Comment updatedComment = commentRepository.save(existingComment);
        return ResponseEntity.ok(updatedComment);
    }

}
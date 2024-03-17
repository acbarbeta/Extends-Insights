package tech.ada.extends_insights.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.ada.extends_insights.domain.entities.Comment;
import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.domain.models.requests.CommentRequest;
import tech.ada.extends_insights.domain.models.requests.UpdateCommentRequest;
import tech.ada.extends_insights.repository.CommentRepository;
import tech.ada.extends_insights.service.CommentService;
import tech.ada.extends_insights.service.exceptions.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Comment createComment(CommentRequest commentRequest) {
        Comment convertedcomment = modelMapper.map(commentRequest, Comment.class);
        return commentRepository.save(convertedcomment);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> getCommentByUserId(User authorId) {
        List<Comment> comments = commentRepository.findByAuthor(authorId);
        if (comments.isEmpty()) {
            throw new ObjectNotFoundException("No comments found");
        } else {
            return comments;
        }
    }

    @Override
    public String updateComment(Long id, UpdateCommentRequest commentRequest) {
        Comment commentToUpdate = commentRepository.findById(id).orElse(null);

        if (commentToUpdate != null) {
            commentToUpdate.setCommentBody(commentRequest.getNewCommentBody());
            commentRepository.save(commentToUpdate);
            return "Comment updated successfully";
        } else {
            return "Comment not found";
        }
    }

    @Override
    public void deleteComment(Long id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            commentRepository.delete(commentOptional.get());
            ResponseEntity.noContent().build();
        } else {
            ResponseEntity.notFound().build();
        }
    }
}

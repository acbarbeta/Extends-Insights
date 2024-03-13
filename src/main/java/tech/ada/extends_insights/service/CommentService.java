package tech.ada.extends_insights.service;


import tech.ada.extends_insights.domain.entities.Comment;
import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.domain.models.requests.CommentRequest;
import tech.ada.extends_insights.domain.models.requests.UpdateCommentRequest;

import java.util.List;

public interface CommentService {
    Comment createComment(CommentRequest commentRequest);
    List<Comment> getAllComments();
    List<Comment> getCommentByUserId(User authorId);
    String updateComment(Long id, UpdateCommentRequest commentRequest);
    void deleteComment(Long id)
}

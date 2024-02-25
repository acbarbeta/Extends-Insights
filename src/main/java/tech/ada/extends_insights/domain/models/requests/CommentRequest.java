package tech.ada.extends_insights.domain.models.requests;

import lombok.Getter;
import tech.ada.extends_insights.domain.entities.User;

@Getter
public class CommentRequest{
    private User author;
    private Long publicationId;
    private String commentBody;

    public CommentRequest(){}

    public CommentRequest(User author, Long publicationId, String commentBody){
        this.author = author;
        this.publicationId = publicationId;
        this.commentBody = commentBody;
    }
}

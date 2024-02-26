package tech.ada.extends_insights.domain.models.requests;

import lombok.Getter;
import tech.ada.extends_insights.domain.entities.User;

@Getter
public class CommentRequest{
    private User author;
    private Long publicationId;
    private String commentBody;
}

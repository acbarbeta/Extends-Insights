package tech.ada.extends_insights.domain.models.requests;

import lombok.Getter;

@Getter
public class CommentRequest{
    private Long userId;
    private Long publicationId;
    private String commentBody;
}

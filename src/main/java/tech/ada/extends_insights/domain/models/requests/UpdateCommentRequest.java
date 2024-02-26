package tech.ada.extends_insights.domain.models.requests;

import lombok.Getter;
import tech.ada.extends_insights.domain.entities.User;

@Getter
public class UpdateCommentRequest {
    private String newCommentBody;
}

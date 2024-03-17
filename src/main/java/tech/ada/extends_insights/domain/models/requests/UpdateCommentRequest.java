package tech.ada.extends_insights.domain.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCommentRequest {
    private String newCommentBody;

    public UpdateCommentRequest(){}

    public UpdateCommentRequest(String newCommentBody) {this.newCommentBody = newCommentBody;}
}

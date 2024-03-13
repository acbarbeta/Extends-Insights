package tech.ada.extends_insights.domain.models.requests;

import lombok.Getter;

@Getter
public class UpdateTagRequest {
    private String title;

    public UpdateTagRequest(){}

    public UpdateTagRequest(String title) {this.title = title;}
}
